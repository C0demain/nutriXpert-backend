package br.com.codemain.nutrixpertai.repository;

import br.com.codemain.nutrixpertai.entity.Session;
import br.com.codemain.nutrixpertai.entity.id.SessionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, SessionId> {

    // Buscar sessão por parâmetros individuais
    Optional<Session> findByAppNameAndUserIdAndId(String appName, String userId, String id);

    // Buscar todas as sessões de um usuário específico
    List<Session> findByAppNameAndUserId(String appName, String userId);

    // Buscar todas as sessões de uma aplicação
    List<Session> findByAppName(String appName);

    // Query para extrair apenas as mensagens do campo JSON 'state'
    @Query(value = "SELECT s.state->'messages' as messages " +
            "FROM sessions s " +
            "WHERE s.app_name = :appName " +
            "AND s.user_id = :userId " +
            "AND s.id = :sessionId",
            nativeQuery = true)
    String getMessagesFromState(@Param("appName") String appName,
                                @Param("userId") String userId,
                                @Param("sessionId") String sessionId);

    // Query para extrair mensagens com informações de sessão
    @Query(value = "SELECT json_build_object(" +
            "'sessionId', s.id, " +
            "'appName', s.app_name, " +
            "'userId', s.user_id, " +
            "'messages', s.state->'messages', " +
            "'createTime', s.create_time, " +
            "'updateTime', s.update_time" +
            ") as session_messages " +
            "FROM sessions s " +
            "WHERE s.app_name = :appName " +
            "AND s.user_id = :userId",
            nativeQuery = true)
    List<String> getSessionsWithMessages(@Param("appName") String appName,
                                         @Param("userId") String userId);

    // Query para buscar a última mensagem de uma sessão
    @Query(value = "SELECT s.state->'messages'->-1 as last_message " +
            "FROM sessions s " +
            "WHERE s.app_name = :appName " +
            "AND s.user_id = :userId " +
            "AND s.id = :sessionId",
            nativeQuery = true)
    String getLastMessage(@Param("appName") String appName,
                          @Param("userId") String userId,
                          @Param("sessionId") String sessionId);
}