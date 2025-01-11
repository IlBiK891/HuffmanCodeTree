import java.util.Map;
import java.util.Scanner;

public class HuffmanExample {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Ввод строки для кодирования
        System.out.println("Enter a message to encode:");
        String message = input.nextLine();

        // Построение дерева Хаффмана и получение кодировок
        HuffmanProcessor processor = new HuffmanProcessor();
        HuffmanTreeNode root = processor.buildTree(message);
        Map<Character, String> encodingMap = processor.generateEncodingMap(root);

        System.out.println("Huffman Codes:");
        encodingMap.forEach((character, code) -> System.out.println(character + ": " + code));

        // Кодирование сообщения
        String encodedMessage = processor.encode(message, encodingMap);
        System.out.println("Encoded Message:");
        System.out.println(encodedMessage);

        // Декодирование сообщения
        String decodedMessage = processor.decode(encodedMessage, encodingMap);
        System.out.println("Decoded Message:");
        System.out.println(decodedMessage);

        // Вывод дерева Хаффмана в виде таблицы смежности
        System.out.println("Huffman Tree (Adjacency List):");
        processor.printAdjacencyList(root);
    }
}

