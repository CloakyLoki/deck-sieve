let tl = gsap.timeline();

tl.from('#header', {y: -100, duration: 1})
    .from('#main', {opacity: .2, duration: 3}, '-=1');

