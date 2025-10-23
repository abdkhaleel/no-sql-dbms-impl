package DB;
import java.util.*;
public class Trie<K> {
    private static class TrieNode<K>{
        Map<Character, TrieNode<K>> children = new HashMap<>();
        Set<K> keyAtNode = new HashSet<>();
    }
    private final TrieNode<K> root;

    public Trie(){
        this.root = new TrieNode<>();
    }

    public void insert(String word, K key){
        TrieNode<K> currentNode = root;
        for(char ch: word.toCharArray()){
            currentNode = currentNode.children.computeIfAbsent(ch, k -> new TrieNode<>());
        }
        currentNode.keyAtNode.add(key);
    }

    public void remove(String word, K key){
        TrieNode<K> currentNode = root;
        for(char ch: word.toCharArray()){
            TrieNode<K> nextNode = currentNode.children.get(ch);
            if(nextNode == null){
                return;
            }
            currentNode = nextNode;
        }
        currentNode.keyAtNode.remove(key);
    }

    public Set<K> findAllWithPrefix(String prefix){
        TrieNode<K> currentNode = root;
        for(char ch: prefix.toCharArray()) {
            TrieNode<K> nextNode = currentNode.children.get(ch);
            if (nextNode == null) {
                return Collections.emptySet();
            }
            currentNode = nextNode;
        }
        Set<K> results = new HashSet<>();
        collectAllKeys(results, currentNode);
        return results;
    }

    private void collectAllKeys(Set<K> results, TrieNode<K> node) {
        if(node == null){
            return;
        }

        results.addAll(node.keyAtNode);

        for(TrieNode<K> child: node.children.values()){
            collectAllKeys(results, child);
        }
    }
}
