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


let modalEdit = document.getElementById('modalEdit');

function openModalEdit(e) {
    modalEdit.classList.add('modal--active');
}

function closeModalEdit() {
    modalEdit.classList.remove('modal--active');
}


let modalEditAdmin = document.getElementById('modalEditAdmin');

function openModalEditAdmin(e) {
    modalEditAdmin.classList.add('modal--active');
}

function closeModalEditAdmin() {
    modalEditAdmin.classList.remove('modal--active');
}


