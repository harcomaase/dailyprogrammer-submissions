use std::env;

fn main() {
    let rounds : u64 = match env::args().skip(1).next() {
        None => { 100000000 },
        Some(x) => { x.parse().expect("not a number!") }
    };

    let mut pi : f64 = 1.;
    //Leibnitz formula
    for i in 1..rounds {
        let f = 1. / ((i as f64 * 2.) + 1.);
        pi += if i % 2 == 0 { f } else { -f };
    }
    pi *= 4.;
    println!("{}", pi);
}
