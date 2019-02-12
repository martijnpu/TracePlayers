package NL.martijnpu.TracePlayers;

import java.io.*;
import java.util.List;

class FileManager {
    static boolean saveFile(String name, List<String> data) {
        FileWriter writer = null;

        try {
            File path = new File(main.get().getDataFolder().getPath());
            File file = new File(main.get().getDataFolder().getPath(), name + ".csv");
            path.mkdirs();

            writer = new FileWriter(file);
            for (String line : data) {
                writer.write(line + "\r\n");
            }
            return true;
        } catch(IOException ex) {
            main.get().sendConsole("[ERROR] Could not save " + ex.getMessage());
            return false;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("PrintWriter not open");
            }
        }
    }

    static boolean checkExists(String name){
        File file = new File(main.get().getDataFolder().getPath(), name + ".csv");
        return file.exists();
    }
}
