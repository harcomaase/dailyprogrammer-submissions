extern crate rand;

use rand::Rng;

fn main() {
    let pieces : Vec<char> = vec!('O', 'I', 'S', 'Z', 'L', 'J', 'T');

    let mut output = String::new();
    let mut rnd = rand::thread_rng();

    let mut current_pieces = pieces.clone();

    while output.len() < 50 {
        if current_pieces.is_empty() {
            current_pieces = pieces.clone();
        }
        let index = (rnd.next_f32() * current_pieces.len() as f32).floor() as usize;
        output.push(current_pieces.remove(index));
    }

    println!("{}", output);
}
