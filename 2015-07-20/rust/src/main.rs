extern crate rand;

fn main() {
 faro(vec!["1","2","3","4","5","6","7","8"]); 
 faro(vec!["raspberry","blackberry","nectarine","kumquat","grapefruit","cherry","raspberry","apple","mango","persimmon","dragonfruit"]); 
 faro(vec!["a","e","i","o","u"]); 
}

fn faro(list: Vec<&str>) {
  let middle = (list.len() / 2) + (list.len() % 2);
  println!("{}", middle);

  let mut shuffled = Vec::new();
  for i in 0..middle {
    let alternate_push = rand::random();
    if alternate_push {
      shuffled.push(list[i]);
    }
    if i + middle < list.len() {
      shuffled.push(list[i+middle]);
    }
    if !alternate_push {
      shuffled.push(list[i]);
    }
  }

  println!("{:?}", list);
  println!(" -> {:?}", shuffled);
}
