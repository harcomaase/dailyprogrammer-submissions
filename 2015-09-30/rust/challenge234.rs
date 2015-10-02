use std::io::prelude::Read;
use std::fs::File;

fn main() {

    let dict_words : Vec<String> = read_file("./../../enable1.txt");

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
