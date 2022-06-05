package com.example.lifetracker;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ApplicationViewModel extends AndroidViewModel {
    private Repository repository;
    private final LiveData<List<ToDoItem>> toDoItemList;
    private final LiveData<List<BudgetItem>> budgetItemList;

    public ApplicationViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        toDoItemList = repository.getAllToDoItems();
        budgetItemList = repository.getAllBudgetItems();
    }

    //TO DO
    public void insert(ToDoItem toDoItem){
        repository.insert(toDoItem);
    }

    public void update(ToDoItem toDoItem){
        repository.update(toDoItem);
    }

    public void delete(ToDoItem toDoItem){
        repository.delete(toDoItem);
    }

    public LiveData<List<ToDoItem>> getToDoItemList() {
        return toDoItemList;
    }

    //BUDGET
    public void insert(BudgetItem budgetItem){
        repository.insert(budgetItem);
    }

    public void update(BudgetItem budgetItem){
        repository.update(budgetItem);
    }

    public void delete(BudgetItem budgetItem){
        repository.delete(budgetItem);
    }

    public LiveData<List<BudgetItem>> getBudgetItemList() {
        return budgetItemList;
    }
}
