function qs(id) {
    return document.getElementById(id);
}

function setResult(id, text, isError = false) {
    const el = qs(id);
    el.textContent = text;
    el.style.color = isError ? "#b42318" : "#1f2933";
}

async function callApi(path, params) {
    const query = new URLSearchParams(params).toString();
    const response = await fetch(`${path}?${query}`);
    const data = await response.json();
    if (!response.ok) {
        throw new Error(data.error || "Request failed");
    }
    return data;
}

function initTabs() {
    const buttons = document.querySelectorAll(".tab-button");
    const panels = document.querySelectorAll(".tab-panel");

    buttons.forEach((button) => {
        button.addEventListener("click", () => {
            buttons.forEach((b) => b.classList.remove("active"));
            panels.forEach((p) => p.classList.remove("active"));
            button.classList.add("active");
            qs(button.dataset.tab).classList.add("active");
        });
    });
}

function initActions() {
    qs("length-run").addEventListener("click", async () => {
        try {
            const data = await callApi("/api/length", {
                value: qs("length-value").value,
                system: qs("length-system").value
            });
            setResult("length-result", `Result: ${data.result}`);
        } catch (e) {
            setResult("length-result", e.message, true);
        }
    });

    qs("weight-run").addEventListener("click", async () => {
        try {
            const data = await callApi("/api/weight", {
                value: qs("weight-value").value,
                system: qs("weight-system").value
            });
            setResult("weight-result", `Result: ${data.result}`);
        } catch (e) {
            setResult("weight-result", e.message, true);
        }
    });

    qs("temp-run").addEventListener("click", async () => {
        try {
            const data = await callApi("/api/temperature", {
                value: qs("temp-value").value,
                source: qs("temp-source").value,
                destination: qs("temp-destination").value
            });
            setResult("temp-result", `Result: ${data.result}`);
        } catch (e) {
            setResult("temp-result", e.message, true);
        }
    });

    qs("currency-run").addEventListener("click", async () => {
        try {
            const data = await callApi("/api/currency", {
                amount: qs("currency-amount").value,
                base: qs("currency-base").value.trim().toUpperCase(),
                destination: qs("currency-destination").value.trim().toUpperCase()
            });
            setResult("currency-result", `Result: ${data.result}`);
        } catch (e) {
            setResult("currency-result", e.message, true);
        }
    });

    qs("grades-run").addEventListener("click", async () => {
        try {
            const data = await callApi("/api/grades", {});
            setResult("grades-result", data.message);
        } catch (e) {
            setResult("grades-result", e.message, true);
        }
    });
}

initTabs();
initActions();
