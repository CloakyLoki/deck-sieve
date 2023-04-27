let tl = gsap.timeline();

tl.from('#header', {y: -100, duration: 1})
    .from('#main', {opacity: .2, duration: 3}, '-=1');

let modal =  document.getElementById('modal');

function openModal(e) {
    modal.classList.add('modal--active');
}

function closeModal() {
    modal.classList.remove('modal--active');
}


//event.preventDefault()
