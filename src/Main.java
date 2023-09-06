import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static BlockingQueue<String> queque1 = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> queque2 = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> queque3 = new ArrayBlockingQueue<>(100);

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void main(String[] args) throws InterruptedException {

        Thread textGen = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                String text = generateText("abc", 100000);
                try {
                    queque1.put(text);
                    queque2.put(text);
                    queque3.put(text);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        textGen.start();

        Thread aa = new Thread(() -> {
            int count = 0;
            int max = 0;
            String text;
            try {
                for (int i = 0; i < 10000; i++){
                    text = queque1.take();
                    for (char a : text.toCharArray()) {
                        if (a == 'a') count++;
                    }
                    if (count > max) max = count;
                    count = 0;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Максимальное количество a = " + max + " во всех строках");

        });
        Thread bb = new Thread(() -> {
            int count = 0;
            int max = 0;
            String text;
            try {
                for (int i = 0; i < 10000; i++){
                    text = queque2.take();
                    for (char a : text.toCharArray()) {
                        if (a == 'b') count++;
                    }
                    if (count > max) max = count;
                    count = 0;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Максимальное количество b = " + max + " во всех строках");
        });

        Thread cc = new Thread(() -> {
            int count = 0;
            int max = 0;
            String text;
            try {
                for (int i = 0; i < 10000; i++){
                    text = queque3.take();
                    for (char a : text.toCharArray()) {
                        if (a == 'c') count++;
                    }
                    if (count > max) max = count;
                    count = 0;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Максимальное количество c = " + max + " во всех строках");
        });
        aa.start();
        bb.start();
        cc.start();

        aa.join();
        bb.join();
        cc.join();
    }
}