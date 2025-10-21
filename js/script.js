document.addEventListener("DOMContentLoaded", () => {
  const choices = document.querySelectorAll(".choice");
  const telaAvaliacao = document.getElementById("tela-avaliacao");
  const telaAgradecimento = document.getElementById("tela-agradecimento");
  const progressCircle = document.getElementById("progress");
  const mensagemTexto = document.getElementById("mensagem-texto");

  const total = 879;
  const duracao = 5;

  choices.forEach((choice) => {
    choice.addEventListener("click", () => {
      choices.forEach((c) => c.classList.remove("selected"));
      choice.classList.add("selected");

      const column = choice.dataset.column;

      fetch("http://10.110.18.10:90902/avaliacao", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ coluna: column }),
      })
        .then(async (response) => {
          if (!response.ok) {
            throw new Error(`Erro HTTP ${response.status}`);
          }
          const data = await response.json();
          console.log("Resposta da API:", data);
          mostrarAgradecimento(true);
        })
        .catch((err) => {
          console.error("Falha ao enviar feedback:", err);
          mostrarAgradecimento(false);
        });
    });
  });

  function mostrarAgradecimento(sucesso) {
    telaAvaliacao.style.display = "none";
    telaAgradecimento.style.display = "flex";

    if (sucesso) {
      mensagemTexto.textContent = "🎉 Muito obrigado pelo seu feedback!";
    } else {
      mensagemTexto.textContent =
        "⚠️ Erro ao enviar feedback. Tente novamente mais tarde.";
    }

    iniciarTimer();
  }

  function iniciarTimer() {
    let tempo = duracao;
    progressCircle.style.strokeDashoffset = 0;

    const intervalo = setInterval(() => {
      tempo--;
      const progresso = total * (1 - tempo / duracao);
      progressCircle.style.strokeDashoffset = progresso;

      if (tempo <= 0) {
        clearInterval(intervalo);
        telaAgradecimento.style.display = "none";
        telaAvaliacao.style.display = "flex";
      }
    }, 1000);
  }
});
