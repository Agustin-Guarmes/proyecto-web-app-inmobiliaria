// Deshabilitar el envío de formularios si hay campos no válidos
(() => {
  'use strict'

  // Obtener todos los formularios que tienen clase needs-validation que queremos validar
  const forms = document.querySelectorAll('.needs-validation')

  // Bucle sobre el form para evitar que se envíe si no cumple con las validaciones
  Array.from(forms).forEach(form => {
    form.addEventListener('submit', event => {
      if (!form.checkValidity()) {
        event.preventDefault()
        event.stopPropagation()
      }

      form.classList.add('was-validated')
    }, false)
  })
})()

const passwordInput = document.getElementById('password');
const confirmPasswordInput = document.getElementById('confirmPassword');
const error1Feedback = document.getElementById('error1');
const error2Feedback = document.getElementById('error2');

confirmPasswordInput.addEventListener('input', () => {
  if (confirmPasswordInput.value === '') {
    confirmPasswordInput.setCustomValidity(' ');
    error1Feedback.classList.remove('d-none');
    error2Feedback.classList.add('d-none');
  } else if (confirmPasswordInput.value !== passwordInput.value) {
    confirmPasswordInput.setCustomValidity('invalid');
    error1Feedback.classList.add('d-none');
    error2Feedback.classList.remove('d-none');
  } else {
    confirmPasswordInput.setCustomValidity('');
    error1Feedback.classList.add('d-none');
    error2Feedback.classList.add('d-none');
  }
});