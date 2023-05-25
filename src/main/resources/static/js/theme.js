const saveTheme = (theme) => {
  localStorage.setItem("theme", theme);
};

const loadTheme = () => {
  const savedTheme = localStorage.getItem("theme");
  if (savedTheme) {
    document.querySelector("body").setAttribute("data-bs-theme", savedTheme);
  }
};

const clearTheme = () => {
  document.querySelector("body").setAttribute("data-bs-theme", "light");
  saveTheme("light");
};

const darkTheme = () => {
  document.querySelector("body").setAttribute("data-bs-theme", "dark");
  saveTheme("dark");
};
