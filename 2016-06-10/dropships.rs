use std::io::prelude::*;
use std::io::BufReader;
use std::fs::File;

fn main() {
    count_dropships("example_input.txt");
    count_dropships("challenge_input.txt");
    count_dropships("challenge2_input.txt");
    count_dropships("challenge3_input.txt");
}

fn count_dropships(filename : &str) {
    let (field, dimension) = read_file(filename);
    let mut best_width = 0;
    for y in 0..dimension {
        for x in 0..dimension {
            if !field[x + y * dimension] {
                continue;
            }
            let mut width = 0;
            for cx in x..dimension {
                if field[cx + y * dimension] {
                    width += 1;
                    if width > best_width && check_field(&field, dimension, x, y, width) {
                        best_width = width;
                    }
                } else {
                    break;
                }
            }

        }
    }
    println!("dropships: {}", best_width * best_width);
}

fn check_field(field : &Vec<bool>, dimension : usize, x : usize, y : usize, width : usize) -> bool {
    if x + width > dimension || y + width > dimension {
        return false;
    }
    let mut field_clear = true;
    for cy in y..y + width {
        for cx in x..x + width {
            if !field[cx + cy * dimension] {
                field_clear = false;
                break;
            }
        }
        if !field_clear {
            break;
        }
    }
    return field_clear;
}

fn read_file(filename : &str) -> (Vec<bool>, usize) {
    let file = File::open(filename).ok().expect("could not read file!");
    let mut reader = BufReader::new(&file);

    let mut line = String::new();
    reader.read_line(&mut line).unwrap();
    let dimension = line.trim().parse().ok().expect("expected a number on first line");

    let mut result = Vec::new();
    for _ in 0..dimension {
        line = String::new();
        reader.read_line(&mut line).unwrap();
        for b in line.chars().take(dimension).map(|c| c == '-') {
            result.push(b);
        }
    }
    return (result, dimension);
}
