use std::io::prelude::Read;
use std::fs::File;

struct TreeElement {
    letter : char,
    children : Vec<TreeElement>
}

impl TreeElement {
    fn add_child(&mut self, child : TreeElement) {
        self.children.push(child);
    }
}

fn main() {

    println!("a: {}", 'a' as u16 - 'a' as u16);
    println!("z: {}", 'z' as u16 - 'a' as u16);

    let dict_words : Vec<String> = read_file("./../../enable1.txt");

    let tree_root = create_tree(&dict_words);

    let input : Vec<String> = read_file("./input.txt");
    for input_word in &input {
        let okay_until : usize = okay_until(input_word, &dict_words);
        if okay_until == input_word.len() {
            println!("{} -> correct", input_word);
        } else {
            println!("{}<{}",&input_word[0..okay_until], &input_word[okay_until..input_word.len()]);
        }
    }
}

fn create_tree(words : &Vec<String>) -> TreeElement {
    let mut root = TreeElement {letter : '#', children : Vec::with_capacity(26)};
    for word in words {
        let mut current_node = &mut root;
        for c in word.chars() {
            let index = char_to_index(c);

            let has_child = false;
            for tree_element in current_node.children {
                if tree_element.letter == c {
                    has_child = true;
                    break;
                }
            }

            if !has_child {
                current_node.add_child(TreeElement {letter : c, children : Vec::with_capacity(26)});
            }
            current_node = &mut current_node.children[index];
        }
    }

    return TreeElement {letter : 'a', children : Vec::with_capacity(26)};
}

fn has_child(tree_elements : &Vec<TreeElement>, c : char) -> bool {
    for tree_element in tree_elements {
        if tree_element.letter == c {
            return true;
        }
    }
    return false;
}

fn char_to_index(c : char) -> usize {
    return 'a' as usize - c as usize;
}

fn okay_until(word : &str, dict_words : &Vec<String>) -> usize {
    let word_length = word.len();
    for i in 0..word_length {
        let start : &str = &word[0..i];
        let mut okay = false;
        for dict_word in dict_words {
            if dict_word.starts_with(start) {
                okay = true;
                break;
            }
        }
        if !okay {
            return i;
        }
    }
    word_length
}

fn read_file(name : &str) -> Vec<String> {
    let mut file : File = File::open(name).ok().expect("could not open file");

    let mut file_contents = String::new();
    file.read_to_string(&mut file_contents).ok().expect("could not read file");

    let mut words : Vec<String> = Vec::new();
    for word in file_contents.trim().split("\n") {
        let new_word = String::from(word);
        words.push(new_word);
    }

    return words;
}
