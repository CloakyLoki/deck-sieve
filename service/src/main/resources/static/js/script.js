let tl = gsap.timeline();

tl.from('#header', {y: -100, duration: 1})
    .from('#main', {opacity: .2, duration: 3}, '-=1');

//модалка
let modalRegister =  document.getElementById('modalRegister');

function openModalRegister(e) {
    modalRegister.classList.add('modal--active');
}

function closeModalRegister() {
    modalRegister.classList.remove('modal--active');
}


let modalEnter =  document.getElementById('modalEnter');

function openModalEnter(e) {
    modalEnter.classList.add('modal--active');
}

function closeModalEnter() {
    modalEnter.classList.remove('modal--active');
}


