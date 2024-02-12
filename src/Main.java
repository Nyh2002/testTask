//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
//import java.util.Arrays;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    static String fileInt = "integers.txt", fileFl = "floats.txt", fileStr = "strings.txt";
    static List<Integer> outputDateInt = new ArrayList<>();
    static List<Double> outputDateFl = new ArrayList<>();
    static List<String> outputDateStr = new ArrayList<>();

    // сортировка по файлам
    public static void elementSort(List<String> outputDate){
        for(int i = 0; i < outputDate.size(); i++) {
            try {// целое число
                outputDateInt.add(Integer.parseInt(outputDate.get(i)));

            } catch (NumberFormatException e1) {
                try { // вещественное число
                    outputDateFl.add(Double.valueOf(outputDate.get(i)));
                } catch(NumberFormatException e2){
                    //это не число - строка
                    outputDateStr.add(outputDate.get(i));
                }
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]); // считывание аргумента до пробела
        }
        String path_to_result = "C:\\Users\\nyh02\\OneDrive\\javatest\\testTask\\src\\", prefix_to_result ="", file_name;
        List<String> outputDate = new ArrayList<>(); // лист считываемых данных из текущего файла
        boolean flagA = false, flagS = false, flagF = false;
        // обработка считанных опций и имен файлов
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case ("-o"):
                    i++;
                    if(i >= args.length) {
                        System.out.println("Error - no files");
                        return;
                    }
                    path_to_result = args[i] + '\\';
                    System.out.println(path_to_result);
                    break;
                case ("-p"):
                    i++;
                    if(i >= args.length) {
                        System.out.println("Error - no files");
                        return;
                    }
                    prefix_to_result = args[i];
                    if(prefix_to_result.charAt(prefix_to_result.length() - 1) != '-') {
                        System.out.println("Parameter -p setting error");
                        return;
                    }
                    fileInt = prefix_to_result + fileInt;
                    fileFl = prefix_to_result + fileFl;
                    fileStr = prefix_to_result + fileStr;
                    break;
                case ("-s"):
                    flagS = true;
                    break;
                case ("-f"):
                    flagF = true;
                    break;
                case ("-a"):
                    flagA = true;
                    break;
                default:
                    file_name = args[i];
                    if(file_name.contains(".txt")) {
                        // добавление всех элементов файла в лист
                        try {
                            FileReader fileReader = new FileReader(file_name);
                            BufferedReader bufferedReader = new BufferedReader(fileReader);

                            String line;
                            while ((line = bufferedReader.readLine()) != null) {
                                outputDate.add(line);
                            }

                            bufferedReader.close();
                        } catch (IOException e) {
                            System.out.println("Ошибка при чтении файла");
                            e.printStackTrace();
                        }
                        // сортировка элементов на три группы
                        elementSort(outputDate);
                        break;
                    }
            }
        }
        // создание файлов для записи
        // целые числа
        try {
            File file = new File(path_to_result + fileInt);
            if (!file.exists()) {
                file.createNewFile();
            }
            // вывод элементов в файлы
            FileWriter writer = new FileWriter(file, flagA);
            for (int i = 0; i < outputDateInt.size(); i++){
                //writer.write(outputDateInt.get(i)); вместо чисел вывод символов
                writer.write(outputDateInt.get(i).toString()); //корректный вывод
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Возникла ошибка во время записи, проверьте данные.");
        }
        // вещественные
        try {
            File file = new File(path_to_result + fileFl);
            if (!file.exists()) {
                file.createNewFile();
            }
            // вывод элементов в файлы
            FileWriter writer = new FileWriter(file, flagA);
            writer.write("");
            for (int i = 0; i < outputDateFl.size(); i++){
                writer.write(String.valueOf(outputDateFl.get(i)));
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Возникла ошибка во время записи, проверьте данные.");
        }
        // строчные значения
        try {
            File file = new File(path_to_result + fileStr);
            if (!file.exists()) {
                file.createNewFile();
            }
            // вывод элементов в файлы
            FileWriter writer = new FileWriter(file, flagA);
            for (int i = 0; i < outputDateStr.size(); i++){
                writer.write(outputDateStr.get(i));
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Возникла ошибка во время записи, проверьте данные.");
        }
        // выполнение опций
        if(flagS) {
            System.out.println("file name | number");
            System.out.println(fileInt + " " + outputDateInt.size());
            System.out.println(fileFl + " " + outputDateFl.size());
            System.out.println(fileStr + " " + outputDateStr.size());
        }
        if(flagF) {
            System.out.println("file name | number | min | max");
            if(outputDateInt.size() != 0 ) {
                System.out.println(fileInt + " " + outputDateInt.size() + " " + Collections.min(outputDateInt) + " " + Collections.max(outputDateInt));
            }
            else{
                System.out.println(fileInt + " " + 0 + " " + 0 + " " + 0);
            }
            if(outputDateFl.size() != 0 ) {
                System.out.println(fileFl + " " + outputDateFl.size()+ " " + Collections.min(outputDateFl) + " " + Collections.max(outputDateFl));
            }
            else{
                System.out.println(fileFl + " " + 0 + " " + 0 + " " + 0);
            }
            int max_size = 0, min_size = 0;
            if(outputDateStr.size() !=0) {
                // цикл поиска короткого элемента

                min_size = outputDateStr.get(0).length();
                for (int i = 1; i < outputDateStr.size(); i++) {
                    if(min_size > outputDateStr.get(i).length()){
                        min_size = outputDateStr.get(i).length();
                    }
                }
                // цикл поиска максимального элемента
                max_size = outputDateStr.get(0).length();
                for (int i = 1; i < outputDateStr.size(); i++) {
                    if(max_size < outputDateStr.get(i).length()){
                        max_size = outputDateStr.get(i).length();
                    }
                }
                if(outputDateStr.size() != 0 ) {
                    System.out.println(fileStr + " " + outputDateStr.size()+ " " + min_size + " " + max_size);
                }
                else{
                    System.out.println(fileStr + " " + 0 + " " + 0 + " " + 0);
                }
            }
        }
    }
}