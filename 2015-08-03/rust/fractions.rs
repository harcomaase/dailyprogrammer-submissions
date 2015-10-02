use std::io;

fn main () {

    let mut line = String::new();
    io::stdin().read_line(&mut line).ok().expect("failed to read line");
    let num_lines = line.trim().parse::<u32>().unwrap();


    let mut numerator = 0;
    let mut denominator = 1;

    for i in 0..num_lines {
        let mut fraction = String::new();
        io::stdin().read_line(&mut fraction).ok().expect("failed to read line");
        let values : Vec<&str> = fraction.trim().split("/").collect();

        let add_num = values[0].parse::<u64>().unwrap();
        let add_den = values[1].parse::<u64>().unwrap();

        let (n1, d1) = add(numerator, denominator, add_num, add_den);
        numerator = n1;
        denominator = d1;
    }

    println!("");
	println!("result: {} / {}", numerator, denominator);
}

fn add(numerator1 : u64, denomiator1 : u64, numerator2 : u64, denomiator2 : u64) -> (u64, u64) {
    let gcd = gcd(denomiator1, denomiator2);  //greates common divisor
    let lcm = denomiator1 * denomiator2 / gcd; //least common multiple
    let a = lcm /denomiator1;
    let b = lcm / denomiator2;
    reduce(numerator1 * a + numerator2 * b, lcm)
}

fn reduce(numerator : u64, denomiator : u64) -> (u64, u64) {
    let gcd = gcd(numerator, denomiator);
    (numerator / gcd, denomiator / gcd)
}

fn gcd(a : u64, b :u64) -> u64 {
	if b == 0 {
		return a;
	}
	return gcd(b, a % b);
}
