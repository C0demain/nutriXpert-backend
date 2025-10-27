package br.com.codemain.nutrixpertai.entity;

import br.com.codemain.nutrixpertai.enums.GoalType;
import jakarta.persistence.*;

@Entity
@Table(name = "goals")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", nullable = true)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "goal_type", nullable = false)
    private GoalType goalType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "target_weight")
    private Double targetWeight;

    @Column(name = "target_calories")
    private int targetCalories;

    @Column(name = "food_restrictions", length = 500)
    private String foodRestrictions;

    public Goal() {
    }

    public Goal(GoalType goalType, User user) {
        this.goalType = goalType;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GoalType getGoalType() {
        return goalType;
    }

    public void setGoalType(GoalType goalType) {
        this.goalType = goalType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(Double targetWeight) {
        this.targetWeight = targetWeight;
    }

    public int getTargetCalories() {
        return targetCalories;
    }

    public void setTargetCalories(int targetCalories) {
        this.targetCalories = targetCalories;
    }

    public String getFoodRestrictions() {
        return foodRestrictions;
    }

    public void setFoodRestrictions(String foodRestrictions) {
        this.foodRestrictions = foodRestrictions;
    }
}