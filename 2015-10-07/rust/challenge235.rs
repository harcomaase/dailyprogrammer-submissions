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
    let separated_input : Vec<&str> = input.trim().split(" ").collect();
    let mut total_score = 0;
    let mut last_one_was_spare = false;
    let mut pre_last_one_was_spare = false;
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
            if (i < 9 || current_char_count == 1) || (i == 9 && current_char_count == 2 && last_score != 10) {
                //always add, except when it's the last frame, and the first roll was a strike
                total_score += this_score;
            }

            if ((last_score == 10 || last_one_was_spare) && current_char_count != 3) || (current_char_count == 3 && last_one_was_spare)  {
                //add score again, if last one was spare or strike, but not in the last roll of the last frame
                // ..., except when the second roll (of the last frame) was a spare
                total_score += this_score;
            }
            if pre_last_score == 10 && !pre_last_one_was_spare {
                //add score again for strikes two rolls ago. but not if it was a -/ spare
                total_score += this_score;
            }
            pre_last_score = last_score;
            last_score = this_score;
            pre_last_one_was_spare = last_one_was_spare;
            last_one_was_spare = c == '/';
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
        result.push(String::from(line).replace("  ", " "));
    }
    return result;
}
