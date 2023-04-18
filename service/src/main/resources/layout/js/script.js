let tl = gsap.timeline();

tl.from('#header', {y: -100, duration: 1})
    .from('#main', {opacity: .2, duration: 1}, '-=1')
    .from('#f-1', {x: -800, opacity: 0, duration: .1})
    .from('#f-2', {x: 800, opacity: 0, duration: .1})
    .from('#f-3', {x: -800, opacity: 0, duration: .1})
    .from('#f-4', {x: 800, opacity: 0, duration: .1})
    .from('#f-5', {x: -800, opacity: 0, duration: .1})
    .from('#f-6', {x: 800, opacity: 0, duration: .1})
    .from('#f-7', {x: -800, opacity: 0, duration: .1})
    .from('#f-8', {x: 800, opacity: 0, duration: .1})
    .from('#f-btn', {y: 800, opacity: 0, duration: .1});

