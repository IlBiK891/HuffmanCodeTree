import java.util.Map;
import java.util.Scanner;

public class HuffmanExample {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);


        System.out.println("Enter a message to encode:");
        String message = input.nextLine();

        // Построение дерева Хаффмана и получение кодировок
        HuffmanProcessor processor = new HuffmanProcessor();
        HuffmanTreeNode root = processor.buildTree(message);
        Map<Character, String> encodingMap = processor.generateEncodingMap(root);

        System.out.println("Huffman Codes:");
        encodingMap.forEach((character, code) -> System.out.println(character + ": " + code));

        String encodedMessage = processor.encode(message, encodingMap);
        System.out.println("Encoded Message:");
        System.out.println(encodedMessage);

        String decodedMessage = processor.decode(encodedMessage, encodingMap);
        System.out.println("Decoded Message:");
        System.out.println(decodedMessage);
    }
}