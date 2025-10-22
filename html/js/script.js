document.addEventListener("DOMContentLoaded", () => {
  const choices = document.querySelectorAll(".choice");
  const telaAvaliacao = document.getElementById("tela-avaliacao");
  const telaAgradecimento = document.getElementById("tela-agradecimento");
  const progressCircle = document.getElementById("progress");
  const mensagemTexto = document.getElementById("mensagem-texto");
  const total = 879;
  const duracao = 5;

  choices.forEach((choice) => {
    choice.addEventListener("click", handleChoice);
    choice.addEventListener("touchstart", handleChoice, { passive: true }); // touch para tablet
  });

  choices.forEach((choice) => {
    choice.addEventListener("click", handleChoice);
    choice.addEventListener("touchstart", handleChoice, { passive: true }); // tablet
  });

  choices.forEach((choice) => {
    choice.addEventListener("click", handleChoice);
    choice.addEventListener("touchstart", handleChoice, { passive: false }); // passive false permite preventDefault
  });

  function handleChoice(e) {
    e.preventDefault(); // evita seleção de texto no touch
    const choice = e.currentTarget;
    choices.forEach((c) => c.classList.remove("selected"));
    choice.classList.add("selected");

    const column = choice.dataset.column;

    fetch("/api/avaliacao", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ coluna: column }),
    })
      .then(async (response) => {
        if (!response.ok) throw new Error(`Erro HTTP ${response.status}`);
        await response.json();
        mostrarAgradecimento(true);
      })
      .catch(() => mostrarAgradecimento(false));
  }

  function mostrarAgradecimento(sucesso) {
    telaAvaliacao.style.display = "none";
    telaAgradecimento.style.display = "flex";
    mensagemTexto.textContent = sucesso
      ? "🎉 Muito obrigado pelo seu feedback!"
      : "⚠️ Erro ao enviar feedback. Tente novamente mais tarde.";
    iniciarTimer();
  }

  function iniciarTimer() {
    let tempo = duracao;
    progressCircle.style.strokeDashoffset = 0;

    const intervalo = setInterval(() => {
      tempo--;
      progressCircle.style.strokeDashoffset = total * (1 - tempo / duracao);

      if (tempo <= 0) {
        clearInterval(intervalo);
        telaAgradecimento.style.display = "none";
        telaAvaliacao.style.display = "flex";
        choices.forEach((c) => c.classList.remove("selected"));
        document.activeElement.blur();
      }
    }, 1000);
  }
});
