use std::io::prelude::Read;
use std::fs::File;

struct TreeElement {
    letter : char,
    nodes : Vec<Option<TreeElement>>
}

impl TreeElement {
    fn init(&mut self, size : u16) {
        for i in 0..size {
            self.nodes.push(None);
        }
    }
}

fn main() {

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
    let mut root = TreeElement {letter : '#', nodes : Vec::with_capacity(26)};
    root.init(26);
    for word in words {
        recursive_add(&word, &mut root);
    }
    return root;
}

fn recursive_add(word : &str, node : &mut TreeElement) {
    let c : char = match word.chars().nth(0) {
        None => return,
        Some(x) => x
    };
    let index = char_to_index(c);

    {
        let mut opt : &Option<TreeElement> = &node.nodes[index];
        match opt {
            &None => {},
            &Some(ref mut sub_node) => {recursive_add(& word[1..word.len()], &mut sub_node); return;}
        }
    }

    let mut new_element = &mut TreeElement {letter : c, nodes : Vec::with_capacity(26)};
    new_element.init(26);
    node.nodes[index] = Some(*new_element);
    recursive_add(&word[1..word.len()], &mut new_element);
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
