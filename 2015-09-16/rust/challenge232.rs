use std::io::prelude::Read;
use std::fs::File;
use std::f64;

fn main () {
    let coords = read_file("./../challenge2.txt");

    let mut smallest_distance : f64 = f64::MAX;
    let mut nearest_index_one = 0;
    let mut nearest_index_two = 0;

    let count = coords.len();

    for a in 0..count {
        for b in 0..count {
            if a == b {
                continue;
            }
            let (x1,y1) = coords[a];
            let (x2,y2) = coords[b];

            let distance = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);
            if distance < smallest_distance {
                smallest_distance = distance;
                nearest_index_one = a;
                nearest_index_two = b;
            }
        }
    }

    let (x1, y1) = coords[nearest_index_one];
    let (x2, y2) = coords[nearest_index_two];
    println!("({}, {}) ({}, {})", x1, y1, x2, y2);
}

fn read_file(name : &str) -> Vec<(f64,f64)> {
    let mut file : File = File::open(name).ok().expect("could not open file");

    let mut file_contents = String::new();
    file.read_to_string(&mut file_contents).ok().expect("could not read file");

    let mut lines = file_contents.trim().split("\n");

    let number_of_lines = lines.next().expect("oh no").trim().parse().ok().expect("not a number");

    let mut coords : Vec<(f64,f64)> = Vec::new();
    for i in 0..number_of_lines  {
        let line = lines.next().expect("not enough lines");
        let mut banana_split = line[1..line.len()-1].split(",");
        coords.push(
            (banana_split.next().expect("wrong format").trim().parse().ok().expect("not a number"),
            banana_split.next().expect("wrong format").trim().parse().ok().expect("not a number"))
         );
    }

    return coords;
}
