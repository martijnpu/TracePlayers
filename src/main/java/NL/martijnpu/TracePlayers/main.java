package NL.martijnpu.TracePlayers;

import org.bukkit.plugin.java.JavaPlugin;

public class main extends  JavaPlugin{
    private static main instance;
    private static RunnableTask runnableTask;

    static main get() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        runnableTask = new RunnableTask();
        sendConsole("We're up and running");
    }

    @Override
    public void onDisable() {
        runnableTask.saveAllPlayerData();
        sendConsole(String.format("Disabled %s successful", this.getName()));
    }

    void sendConsole(String message){
            System.out.println("["+ getDescription().getName() + "] " + message);
    }
}
