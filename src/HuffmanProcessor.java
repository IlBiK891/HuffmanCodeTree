import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class HuffmanProcessor {

    public HuffmanTreeNode buildTree(String message) {
        // Подсчёт частоты символов
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char ch : message.toCharArray()) {
            frequencyMap.put(ch, frequencyMap.getOrDefault(ch, 0) + 1);
        }

        // Очередь с приоритетом для узлов
        PriorityQueue<HuffmanTreeNode> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.frequency));

        // Заполнение очереди узлами
        frequencyMap.forEach((character, frequency) -> queue.add(new HuffmanTreeNode(character, frequency)));

        // Построение дерева
        while (queue.size() > 1) {
            HuffmanTreeNode left = queue.poll();
            HuffmanTreeNode right = queue.poll();

            HuffmanTreeNode parent = new HuffmanTreeNode(null, left.frequency + right.frequency);
            parent.left = left;
            parent.right = right;

            queue.add(parent);
        }

        return queue.poll();
    }

    public Map<Character, String> generateEncodingMap(HuffmanTreeNode root) {
        Map<Character, String> encodingMap = new HashMap<>();
        createEncoding(root, "", encodingMap);
        return encodingMap;
    }

    private void createEncoding(HuffmanTreeNode node, String currentCode, Map<Character, String> map) {
        if (node == null) return;

        if (node.character != null) {
            map.put(node.character, currentCode);
        }

        createEncoding(node.left, currentCode + "0", map);
        createEncoding(node.right, currentCode + "1", map);
    }

    public String encode(String message, Map<Character, String> encodingMap) {
        StringBuilder encoded = new StringBuilder();
        for (char ch : message.toCharArray()) {
            encoded.append(encodingMap.get(ch));
        }
        return encoded.toString();
    }

    public String decode(String encodedMessage, Map<Character, String> encodingMap) {
        Map<String, Character> decodingMap = new HashMap<>();
        encodingMap.forEach((character, code) -> decodingMap.put(code, character));

        StringBuilder decoded = new StringBuilder();
        StringBuilder currentCode = new StringBuilder();

        for (char bit : encodedMessage.toCharArray()) {
            currentCode.append(bit);
            if (decodingMap.containsKey(currentCode.toString())) {
                decoded.append(decodingMap.get(currentCode.toString()));
                currentCode.setLength(0);
            }
        }

        return decoded.toString();
    }
}