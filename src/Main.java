import java.io.*;//импорт пакета, содержащего классы для ввода/вывода
import java.net.*;//импорт пакета, содержащего классы для работы в сети
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
    static String sportsmanInfo = "";

    public static void printMenu(){
        System.out.println("~~~~~~МЕНЮ~~~~~~");
        System.out.println("1. Ввести длины прыжков спортсменов");
        System.out.println("2. Узнать призовые места");
        System.out.println("3. Выход из программы");
    }

    public static void main(String[] arg) {
        try {


            Socket clientSocket = new Socket("127.0.0.1", 2525);//установление соединения между локальной машиной и указанным портом узла сети
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));//создание буферизированного символьного потока ввода
            ObjectOutputStream coos = new ObjectOutputStream(clientSocket.getOutputStream());//создание потока вывода
            ObjectInputStream cois = new ObjectInputStream(clientSocket.getInputStream());//создание потока ввода

            System.out.println("Установлено соединение с сервером!");

            Scanner s = new Scanner(System.in);
            int choice;

            while(true){
                printMenu();
                choice = s.nextInt();
                switch(choice){
                    case 1:{
                        System.out.println("Введите идентификационный номер спортсмена:");
                        sportsmanInfo += stdin.readLine();
                        sportsmanInfo +="/";
                        System.out.println("Введите результат прыжка спортсмена (см):");
                        sportsmanInfo += stdin.readLine();
                        System.out.println("Идентификационный номер/результат: " );
                        coos.writeObject(sportsmanInfo);//потоку вывода присваивается значение строковой переменной (передается серверу)
                        System.out.println("~server~: " + cois.readObject());//выводится на экран содержимое потока ввода (переданное сервером)

                        break;
                    }
                    case 2:{
                        coos.writeObject("top_3_places");
                        System.out.println("~server~: " + cois.readObject());//выводится на экран содержимое потока ввода (переданное сервером)
                        break;
                    }
                    case 3:{
                        exit(0);
                    }
                    default:
                        System.out.println("Неверный выбор!");
                }
                coos.close();//закрытие потока вывода
                cois.close();//закрытие потока ввода
                clientSocket.close();//закрытие сокета
            }


        } catch (Exception e) {
            e.printStackTrace();//выполнение метода исключения е
        }
    }
}
