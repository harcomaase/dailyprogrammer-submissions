use std::io::prelude::Read;
use std::fs::File;

fn main() {
    let inputs = read_file("./../challenge.txt");

    for i in 0..inputs.len() {
        let (x, y) = inputs[i];
        let is_ruth_aaron_pair = (x + 1 == y) && (get_primefactor_sum(x) == get_primefactor_sum(y));

        println!("({},{}) {}VALID", x, y, if !is_ruth_aaron_pair {"IN"} else {""} );
    }
}

fn get_primefactor_sum(n : u32) -> u32 {
    let mut sum = 0;
    let mut rest = n;
    for i in 2..rest + 1 {
        if rest % i == 0 {
            sum += i;
            while rest % i == 0 {
                rest /= i;
            }
        }
    }
    return sum;
}

fn read_file(name : &str) -> Vec<(u32,u32)> {
    let mut file : File = File::open(name).ok().expect("could not open file");

    let mut file_contents = String::new();
    file.read_to_string(&mut file_contents).ok().expect("could not read file");

    let mut lines = file_contents.trim().split("\n");

    let number_of_lines = lines.next().expect("oh no").trim().parse().ok().expect("not a number");

    let mut result : Vec<(u32,u32)> = Vec::new();
    for i in 0..number_of_lines  {
        let line = lines.next().expect("not enough lines");
        let mut banana_split = line[1..line.len()-1].split(",");
        result.push(
            (banana_split.next().expect("wrong format").trim().parse().ok().expect("not a number"),
            banana_split.next().expect("wrong format").trim().parse().ok().expect("not a number"))
         );
    }

    return result;
}
