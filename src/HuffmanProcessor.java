import java.util.*;

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

    public void printAdjacencyList(HuffmanTreeNode root) {
        Map<Integer, List<Integer>> adjacencyList = new HashMap<>();
        Map<HuffmanTreeNode, Integer> nodeIds = new HashMap<>();
        List<String> nodeDescriptions = new ArrayList<>();

        generateAdjacencyList(root, adjacencyList, nodeIds, nodeDescriptions, new int[]{0});

        // Вывод узлов
        System.out.println("Nodes:");
        for (String description : nodeDescriptions) {
            System.out.println(description);
        }

        // Вывод смежности
        System.out.println("\nAdjacency List:");
        adjacencyList.forEach((parent, children) -> {
            System.out.print(parent + " -> ");
            System.out.println(children);
        });
    }

    private void generateAdjacencyList(HuffmanTreeNode node, Map<Integer, List<Integer>> adjacencyList,
                                       Map<HuffmanTreeNode, Integer> nodeIds, List<String> nodeDescriptions,
                                       int[] idCounter) {
        if (node == null) return;

        int currentId = idCounter[0]++;
        nodeIds.put(node, currentId);

        if (node.character != null) {
            nodeDescriptions.add(currentId + ": Char = '" + node.character + "', Freq = " + node.frequency);
        } else {
            nodeDescriptions.add(currentId + ": Internal, Freq = " + node.frequency);
        }

        if (node.left != null) {
            adjacencyList.computeIfAbsent(currentId, k -> new ArrayList<>()).add(idCounter[0]);
            generateAdjacencyList(node.left, adjacencyList, nodeIds, nodeDescriptions, idCounter);
        }

        if (node.right != null) {
            adjacencyList.computeIfAbsent(currentId, k -> new ArrayList<>()).add(idCounter[0]);
            generateAdjacencyList(node.right, adjacencyList, nodeIds, nodeDescriptions, idCounter);
        }
    }
}