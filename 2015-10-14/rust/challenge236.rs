use std::env;

fn main() {
    let mut args = env::args().skip(1);
    let target_number = parse_number(args.next());
    let mut fib_numbers : Vec<u64> = Vec::new();

    let mut i = 0;
    loop {
        let next_number : u64 = match i {
            0 => { 0 },
            1 => { 1 },
            _ => { &fib_numbers[i - 1] + &fib_numbers[i - 2] }
        };
        if next_number > target_number {
            break;
        }
        i += 1;
        fib_numbers.push(next_number);
        if next_number == target_number {
            print_numbers(&fib_numbers, target_number, 1);
            return;
        }
    }



    for fib_number in fib_numbers.iter().rev() {
        let mut multiplier = 2;
        loop {
            let number = fib_number * multiplier;
            if number == target_number {
                print_numbers(&fib_numbers, target_number, multiplier);
                return;
            }
            if number > target_number {
                break;
            }
            multiplier += 1;
        }
    }
}

fn print_numbers(fib_numbers : &Vec<u64>, target_number : u64, multiplier : u64) {
    println!("f(1) = {}", multiplier);
    for fib_number in fib_numbers {
        let number = fib_number * multiplier;
        print!("{}", number);
        if number == target_number {
            break;
        }
        print!(", ");
    }
    println!("");
}

fn parse_number(opt : Option<String>) -> u64 {
    return opt.expect("please supply a number").parse().expect("not a number");
}
