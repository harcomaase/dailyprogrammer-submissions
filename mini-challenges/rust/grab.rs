use std::io::BufReader;
use std::io::BufRead;
use std::fs::{self, File};
use std::env;

fn main() {
    let mut args = env::args().skip(1);
    let word = args.next().unwrap().to_lowercase();

    match args.next() {
        None => {
            for dir_entry in fs::read_dir(".").unwrap() {
                let filename : String = dir_entry.unwrap().file_name().into_string().unwrap();
                if filename.to_lowercase().ends_with(".txt") {
                    grab(&word, &filename, true);
                }
            }
        },
        Some(filename) => { grab(&word, &filename, false); }
    }
}

fn grab(word : &str, filename : &str, print_filename : bool) {
    let file=File::open(filename).unwrap();
    let buf_reader = BufReader::new(file);

    let mut line_number = 0;
    for line in buf_reader.lines() {
        line_number += 1;
        let line = line.unwrap();
        if line.to_lowercase().contains(&word) {
            if print_filename {
                println!("{}: {}: {}", filename, line_number, line);
            } else {
                println!("{}: {}", line_number, line);
            }
        }
    }
}
