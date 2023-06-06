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

//validación de la confirmación de la contraseña
const passwordInput = document.getElementById('password');
const confirmPasswordInput = document.getElementById('confirmPassword');
const error1Feedback = document.getElementById('error1');
//const error2Feedback = document.getElementById('error2');
const iconValidatorSize = document.getElementById('icon');

iconValidatorSize.addEventListener('change',
  function (){
        if(iconValidatorSize.files[0].size >= 1048576){
            iconValidatorSize.setCustomValidity('Tamaño de imagen no soportado');
        } else {
            iconValidatorSize.setCustomValidity('');
        }
        fileValidation();
  })

  function fileValidation(){
      let filePath = iconValidatorSize.value,
          allowefExtensions = /(.jpg|.jpeg|.png)$/i;

          console.log(filePath + "  Estoy acaaaaaaaaaaaaaaa!");

      if(!allowefExtensions.exec(filePath)){
          const $divCustomError = document.querySelector('.invalid-iconCustom');
          $divCustomError.innerHTML = 'Carga un archivo de imagen válido(.jpg - .jpeg - .png)';
          iconValidatorSize.setCustomValidity('invalid');
          return false;
      };
  }

function validateConfirmPassword() {
  if (confirmPasswordInput.value === '') {
    confirmPasswordInput.setCustomValidity(' ');
    error1Feedback.classList.remove('d-none');
    //error2Feedback.classList.add('d-none');
  } else if (confirmPasswordInput.value !== passwordInput.value) {
    confirmPasswordInput.setCustomValidity('invalid');
    error1Feedback.classList.add('d-none');
    //error2Feedback.classList.remove('d-none');
  } else {
    confirmPasswordInput.setCustomValidity('');
    error1Feedback.classList.add('d-none');
    //error2Feedback.classList.add('d-none');
  }
}
//vuelve a ejecutar la validación de confirmPassword cada vez que se modifique la contraseña
if (confirmPasswordInput) {
    confirmPasswordInput.addEventListener('input', validateConfirmPassword);
    passwordInput.addEventListener('input', validateConfirmPassword);
    //poder ver la confirmación de la contraseña
    const toggleConfirmPasswordButton = document.getElementById('toggleConfirmPassword');
    if (toggleConfirmPasswordButton) {
    toggleConfirmPasswordButton.addEventListener('click', function () {
      const type = confirmPasswordInput.getAttribute('type') === 'password' ? 'text' : 'password';
      confirmPasswordInput.setAttribute('type', type);

      // Cambiar el icono del ojo
      toggleConfirmPasswordButton.querySelector('i').classList.toggle('bi-eye');
      toggleConfirmPasswordButton.querySelector('i').classList.toggle('bi-eye-slash');
    });
    }
}

//validación de la contraseña para que tenga entre 8 y 20 caracteres, con al menos una letra mayúscula, una letra minúscula y un dígito
if (passwordInput) {
passwordInput.addEventListener('input', () => {
  let password = passwordInput.value;
  if (password.length < 8 || password.length > 20 ||
          !/[A-Z]/.test(password) ||
          !/[a-z]/.test(password) ||
          !/\d/.test(password)) {
          passwordInput.setCustomValidity('invalid');
  } else {
    passwordInput.setCustomValidity('');
  }
});
    //poder ver la contraseña
    const togglePasswordButton = document.getElementById('togglePassword');
    if (togglePasswordButton) {
    togglePasswordButton.addEventListener('click', function () {
      const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
      passwordInput.setAttribute('type', type);

      // Cambiar el icono del ojo
      togglePasswordButton.querySelector('i').classList.toggle('bi-eye');
      togglePasswordButton.querySelector('i').classList.toggle('bi-eye-slash');
    });
    }
}


//validación del dni para que sea un número de 7 a 9 dígitos
var dniInput = document.getElementById('dni');
if (dniInput) {
    dniInput.addEventListener('input', () => {
      var dni = dniInput.value.trim();

      if (!/^\d{7,9}$/.test(dni)) {
        dniInput.setCustomValidity('invalid');
      } else {
        dniInput.setCustomValidity('');
      }
    });
}

//validación para el correo electronico
var emailInput = document.getElementById('email');
if (emailInput) {
    emailInput.addEventListener('input', () => {
      var email = emailInput.value.trim();

      if (!isValidEmail(email)) {
        emailInput.setCustomValidity('Ingrese un correo electrónico válido');
      } else {
        emailInput.setCustomValidity('');
      }
    });
}

    function isValidEmail(email) {
      var emailRegex = /^[-\w.%+]{1,64}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/i;
      return emailRegex.test(email);
    }
