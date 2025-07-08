document.addEventListener('DOMContentLoaded', function() {
    loadExpenses();
    setupEventListeners();
});

function setupEventListeners() {
    document.getElementById('expenseForm').addEventListener('submit', handleSubmit);
    document.getElementById('categoryFilter').addEventListener('change', handleCategoryFilter);
}

let grafico; // Variável global para o gráfico

function atualizarGrafico(expenses) {
    // Agrupa os valores por categoria
    const categorias = {};
    expenses.forEach(exp => {
        categorias[exp.categoria] = (categorias[exp.categoria] || 0) + exp.valor;
    });

    const labels = Object.keys(categorias);
    const data = Object.values(categorias);

    const ctx = document.getElementById('graficoGastos').getContext('2d');

   
    if (grafico) grafico.destroy();

    grafico = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Total por Categoria',
                data: data,
                backgroundColor: [
                    '#1a73e8', '#f29d38', '#0d904f', '#d93025', '#a0a0a0', '#174ea6'
                ]
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: { display: false }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        callback: function(value) {
                            return 'R$ ' + value.toFixed(2);
                        }
                    }
                }
            }
        }
    });
}

async function loadExpenses() {
    try {
        const response = await fetch('/gastos');
        const expenses = await response.json();
        updateExpensesTable(expenses);
        updateSummary(expenses);
        atualizarGrafico(expenses); 
        atualizarGraficoMensal(expenses);
    } catch (error) {
        console.error('Erro ao carregar gastos:', error);
    }
}

let graficoMensal;

function atualizarGraficoMensal(expenses) {
    const meses = {};

    expenses.forEach(exp => {
        const data = new Date(exp.date);
        if (!isNaN(data.getTime())) {
            const mesAno = data.toLocaleString('default', {
                month: 'short',
                year: 'numeric'
            });
            meses[mesAno] = (meses[mesAno] || 0) + exp.valor;
        }
    });

    const labels = Object.keys(meses);
    const data = Object.values(meses);

    const ctx = document.getElementById('graficoMensal').getContext('2d');

    if (graficoMensal) graficoMensal.destroy();

    graficoMensal = new Chart(ctx, {
        type: 'pie',
        data: {
            labels: labels,
            datasets: [{
                label: 'Gastos por Mês',
                data: data,
                backgroundColor: [
                    '#1a73e8', '#f29d38', '#0d904f', '#d93025',
                    '#a0a0a0', '#174ea6', '#9c27b0', '#4caf50',
                    '#ff9800', '#03a9f4', '#e91e63', '#795548'
                ]
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false, // Permite ajustar altura do canvas
            plugins: {
                legend: {
                    position: 'right', // <--- Altere para 'bottom' se preferir
                    labels: {
                        color: '#fff',
                        font: {
                            size: 14
                        }
                    }
                },
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            const valor = context.parsed;
                            return `${context.label}: R$ ${valor.toFixed(2)}`;
                        }
                    }
                }
            }
        }
    });
}


async function handleCategoryFilter(event) {
    const category = event.target.value;
    try {
        const response = await fetch(
            category ? `/categoria?categoria=${category}` : '/gastos'
        );
        const expenses = await response.json();
        updateExpensesTable(expenses);
        updateSummary(expenses);
        atualizarGrafico(expenses);
        atualizarGraficoMensal(expenses); 
        
    } catch (error) {
        console.error('Erro ao filtrar:', error);
    }
}


function formatarData(date) {
    if (!date) return 'Data não disponível';
    try {
        // Se vier como string yyyy-MM-dd
        if (typeof date === 'string' && /^\d{4}-\d{2}-\d{2}$/.test(date)) {
            const [ano, mes, dia] = date.split('-');
            return `${dia}/${mes}/${ano}`;
        }
        
        const dataFormatada = new Date(date);
        if (!isNaN(dataFormatada.getTime())) {
            const dia = dataFormatada.getDate().toString().padStart(2, '0');
            const mes = (dataFormatada.getMonth() + 1).toString().padStart(2, '0');
            const ano = dataFormatada.getFullYear();
            return `${dia}/${mes}/${ano}`;
        }
        return 'Data inválida';
    } catch (error) {
        console.error('Erro ao formatar data:', error);
        return 'Data inválida';
    }
}

function updateExpensesTable(expenses) {
    // Ordena do mais recente para o mais antigo
    expenses.sort((a, b) => {
        const dataA = new Date(a.date);
        const dataB = new Date(b.date);
        return dataB - dataA;
    });

    const tbody = document.getElementById('expensesTable');
    tbody.innerHTML = expenses.map(expense => `
        <tr>
            <td>
                <span id="desc-${expense.id}">${expense.descricao}</span>
                <input type="text" id="input-desc-${expense.id}" value="${expense.descricao}" style="display:none;width:80%;" />
            </td>
            <td>R$ ${expense.valor.toFixed(2)}</td>
            <td><span class="category-badge">${expense.categoria}</span></td>
            <td>${formatarData(expense.date)}</td>
            <td>
                <button class="btn-icon" onclick="showEditDesc(${expense.id})">Editar</button>
                <button class="btn-icon" onclick="deleteExpense(${expense.id})">Apagar</button>
                <button class="btn-icon" id="btn-save-${expense.id}" style="display:none;" onclick="saveEditDesc(${expense.id})">Salvar</button>
                <button class="btn-icon" id="btn-cancel-${expense.id}" style="display:none;" onclick="cancelEditDesc(${expense.id})">Cancelar</button>
            </td>
        </tr>
    `).join('');
}


function showEditDesc(id) {
    document.getElementById(`desc-${id}`).style.display = 'none';
    document.getElementById(`input-desc-${id}`).style.display = '';
    document.getElementById(`btn-save-${id}`).style.display = '';
    document.getElementById(`btn-cancel-${id}`).style.display = '';
}


function cancelEditDesc(id) {
    document.getElementById(`desc-${id}`).style.display = '';
    document.getElementById(`input-desc-${id}`).style.display = 'none';
    document.getElementById(`btn-save-${id}`).style.display = 'none';
    document.getElementById(`btn-cancel-${id}`).style.display = 'none';
}


async function saveEditDesc(id) {
    const novaDescricao = document.getElementById(`input-desc-${id}`).value;
    try {
        await fetch(`/${id}/descricao`, {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ descricao: novaDescricao })
        });
        loadExpenses();
    } catch (error) {
        alert('Erro ao editar descrição');
    }
}


async function deleteExpense(id) {
    if (!confirm('Tem certeza que deseja apagar este gasto?')) return;
    try {
        await fetch(`/gastos/${id}`, { method: 'DELETE' });
        loadExpenses();
    } catch (error) {
        alert('Erro ao apagar gasto');
    }
}

function updateSummary(expenses) {
    const total = expenses.reduce((sum, exp) => sum + exp.valor, 0);
    const highest = Math.max(...expenses.map(exp => exp.valor));
    const monthly = total / getUniqueMonths(expenses);

    document.getElementById('totalAmount').textContent = `R$ ${total.toFixed(2)}`;
    document.getElementById('highestExpense').textContent = `R$ ${highest.toFixed(2)}`;
    document.getElementById('monthlyAverage').textContent = `R$ ${monthly.toFixed(2)}`;
}

function getUniqueMonths(expenses) {
    const months = new Set(expenses.map(exp => {
        if (!exp.date) return null;
        try {
            return new Date(exp.date).toISOString().substring(0, 7);
        } catch {
            return null;
        }
    }).filter(Boolean));
    return months.size || 1;
}

async function handleSubmit(event) {
    event.preventDefault();
    const formData = {
        descricao: document.getElementById('description').value,
        valor: parseFloat(document.getElementById('amount').value),
        categoria: document.getElementById('category').value,
        data: document.getElementById('date').value
    };

    try {
        await fetch('/gastos', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        });
        
        event.target.reset();
        loadExpenses();
    } catch (error) {
        console.error('Erro ao salvar:', error);
    }
}
