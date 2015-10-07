use std::io::prelude::Read;
use std::fs::File;
use std::env;

fn main() {
    let args : Vec<String> = env::args().collect();
    if args.len() < 2 {
        println!("please supply an input file as argument");
        return;
    }
    for input in read_file(&args[1]) {
        process(input);
    }
}

fn process(input : String) {
    let separated_input : Vec<&str> = input.split(" ").collect();
    let mut total_score = 0;
    let mut add_next = 0;
    let mut last_score = 0;
    let mut pre_last_score = 0;
    for i in 0..separated_input.len() {
        let unparsed_score = separated_input[i];
        let mut current_char_count = 0;
        for c in unparsed_score.chars() {
            current_char_count += 1;
            let this_score =  match c {
                'X' => { 10 },
                '-' => { 0 },
                '/' => { 10 - last_score },
                _ => {c as u16 - '0' as u16 }
            };
            if i < 9 || current_char_count == 1 || current_char_count == 2 && last_score != 10 {
                total_score += this_score;
            }
            if add_next > 0 {
                add_next -= 1;
                total_score += this_score;
                if pre_last_score == 10 && current_char_count != 3 {
                    total_score += this_score;
                }
            }
            add_next = match c {
                'X' => { 2 },
                '/' => { 1 },
                _ => { add_next }
            };
            pre_last_score = last_score;
            last_score = this_score;
        }
    }
    println!("total score: {}", total_score);
}

fn read_file(name : &str) -> Vec<String> {
    let mut file : File = File::open(name).ok().expect("could not open file");

    let mut file_contents = String::new();
    file.read_to_string(&mut file_contents).ok().expect("could not read file");

    let lines : Vec<&str> = file_contents.trim().split("\n").collect();
    let mut result : Vec<String> = Vec::new();
    for line in lines  {
        result.push(String::from(line));
    }
    return result;
}
