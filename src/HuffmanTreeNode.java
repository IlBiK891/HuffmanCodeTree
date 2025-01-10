class HuffmanTreeNode {
    Character character; // Символ, если узел листовой
    int frequency; // Частота символа
    HuffmanTreeNode left, right; // Дочерние узлы

    HuffmanTreeNode(Character character, int frequency) {
        this.character = character;
        this.frequency = frequency;
    }
}