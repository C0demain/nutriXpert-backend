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

    @Column(name = "current_calories")
    private int currentCalories;

    @Column(name = "target_protein")
    private Double targetProtein;

    @Column(name = "current_protein")
    private Double currentProtein;

    @Column(name = "target_carbs")
    private Double targetCarbs;

    @Column(name = "current_carbs")
    private Double currentCarbs;

    @Column(name = "target_fats")
    private Double targetFats;

    @Column(name = "current_fats")
    private Double currentFats;

    @Column(name = "food_restrictions", length = 500)
    private String foodRestrictions;

    public Goal() {
        startGoal();
    }

    public Goal(GoalType goalType, User user) {
        this.goalType = goalType;
        this.user = user;
        startGoal();
    }

    private void startGoal() {
        this.currentCarbs = 0.0;
        this.currentProtein = 0.0;
        this.currentFats = 0.0;
        this.currentCalories = 0;
    }

    //Getters e Setters
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

    public int getCurrentCalories() {
        return currentCalories;
    }

    public void setCurrentCalories(int currentCalories) {
        this.currentCalories = currentCalories;
    }

    public Double getTargetProtein() {
        return targetProtein;
    }

    public void setTargetProtein(Double targetProtein) {
        this.targetProtein = targetProtein;
    }

    public Double getCurrentProtein() {
        return currentProtein;
    }

    public void setCurrentProtein(Double currentProtein) {
        this.currentProtein = currentProtein;
    }

    public Double getTargetCarbs() {
        return targetCarbs;
    }

    public void setTargetCarbs(Double targetCarbs) {
        this.targetCarbs = targetCarbs;
    }

    public Double getCurrentCarbs() {
        return currentCarbs;
    }

    public void setCurrentCarbs(Double currentCarbs) {
        this.currentCarbs = currentCarbs;
    }

    public Double getTargetFats() {
        return targetFats;
    }

    public void setTargetFats(Double targetFats) {
        this.targetFats = targetFats;
    }

    public Double getCurrentFats() {
        return currentFats;
    }

    public void setCurrentFats(Double currentFats) {
        this.currentFats = currentFats;
    }

    public String getFoodRestrictions() {
        return foodRestrictions;
    }

    public void setFoodRestrictions(String foodRestrictions) {
        this.foodRestrictions = foodRestrictions;
    }
}