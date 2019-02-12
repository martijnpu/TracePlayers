package NL.martijnpu.TracePlayers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class RunnableTask {
    private String fileName;
    private List<String> data;
    private List<Integer> dataCounter;

    RunnableTask(){
        data = new ArrayList<>();
        dataCounter = new ArrayList<>();

        createName();

        data.add("DateTime");
        dataCounter.add(0);
        BukkitScheduler scheduler = main.get().getServer().getScheduler();
        scheduler.runTaskTimerAsynchronously(main.get(), this::saveAllPlayerData, 0L, 6000L); // runs every 5 minutes
    }

    void saveAllPlayerData() {
        main.get().sendConsole("Collecting data");
        StringBuilder newRow = new StringBuilder();

        newRow.append(getDateTime());

        int counter = 0;

        for (Player p : Bukkit.getOnlinePlayers() ) {
            newRow.append(",").append(p.getPlayerListName());
            newRow.append(",").append(p.getLocation().getWorld().getName()).append("(").append((int) p.getLocation().getX()).append(" ").append((int) p.getLocation().getY()).append(" ").append((int) p.getLocation().getZ()).append(")");
            counter++;
        }
        data.add(newRow.toString());
        dataCounter.add(counter);

        while (dataCounter.get(0) < counter){
            dataCounter.set(0, dataCounter.get(0) + 1);
            data.set(0, data.get(0) + (",player" + dataCounter.get(0) + ",location" + dataCounter.get(0)));
        }


        for (int i = 1; i < data.size(); i++) {
            while (dataCounter.get(i) < counter) {
                data.set(i, data.get(i) + ",,");
                dataCounter.set(i, dataCounter.get(i) + 1);
            }
        }

        main.get().sendConsole("Saving data");
        if(!FileManager.saveFile(fileName, data)) {
            createName();
            if(!FileManager.saveFile(fileName, data))
                main.get().sendConsole("[ERROR] Unable to save the data. Ignoring it");
        }
    }

    private String getDateTime(){
        Date date = new Date();
        String strDateFormat = "dd-MM-yyyy HH:mm";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        return dateFormat.format(date);
    }

    private String getDate(){
        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        return dateFormat.format(date);
    }

    private void createName(){
        fileName = getDate();

        String tempStartDateTime = fileName;
        int count = 1;

        while(FileManager.checkExists(tempStartDateTime))
        {
            tempStartDateTime = fileName + " (" + count + ")";
            count++;
        }
        fileName = tempStartDateTime;
        main.get().sendConsole("Created new file \"" + fileName + "\"");
    }
}
