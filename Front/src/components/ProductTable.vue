<script setup lang="ts">
import { ref } from "vue";
import { onMounted } from "vue";

// Array de produtos de exemplo
// const produtos = ref([
//   { id: 1, nome: "Tênis de Corrida", descricao: "Confortável e leve", preco: 199.99 },
//   { id: 2, nome: "Camiseta Esportiva", descricao: "Material respirável", preco: 59.99 },
//   { id: 3, nome: "Mochila de Hidratação", descricao: "Capacidade de 2L", preco: 89.99 },
//   // Adicione mais produtos conforme necessário
// ]);

// const produtos = ref([]);

const produtos = ref<Produto[]>([]);
  const mostrarFormulario = ref(false);


interface Produto {
  id: number;
  nome: string;
  descricao: string;
  preco: number;
}

// Método para buscar produtos da API
// GET: Buscar produtos
const fetchProdutos = async () => {
  try {
    const response = await fetch("http://localhost:8085/produtos");
    if (!response.ok) throw new Error("Erro ao carregar produtos");
    const pageData = await response.json();
    produtos.value = pageData.content; // Use o array de content
    console.log(pageData.content); // Verifique se é o array esperado
  } catch (error) {
    console.error("Erro ao buscar produtos:", error);
  }
};


const deletarProduto = async (id) => {
  try {
    const response = await fetch(`http://localhost:8085/produtos/${id}`, {
      method: 'DELETE',
    });
    if (!response.ok) throw new Error('Erro ao deletar produto');

    // Recarregar a lista de produtos após a exclusão
    await fetchProdutos();
  } catch (error) {
    console.error('Erro ao deletar produto:', error);
  }
};

const novoProduto = ref({
  nome: '',
  descricao: '',
  preco: 0,
});

const adicionarProduto = async () => {
  try {
    const response = await fetch('http://localhost:8085/produtos', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(novoProduto.value),
    });
    if (!response.ok) throw new Error('Erro ao adicionar produto');

    // Limpar o formulário
    novoProduto.value = { nome: '', descricao: '', preco: 0 };

    // Recarregar a lista de produtos
    await fetchProdutos();
  } catch (error) {
    console.error('Erro ao adicionar produto:', error);
  }
};




// Executando o fetchProdutos quando o componente é montado
onMounted(fetchProdutos);

const columnsTable = [
  { title: "Código", value: "id" },
  { title: "Nome", value: "nome" },
  { title: "Descrição", value: "descricao" },
  { title: "Preço", value: "preco" },
  { title: "Ações", value: "acoes" },
];
</script>

<template>
  <v-container class="bg-surface">
    <v-row align="center">
      <v-spacer></v-spacer>
      <v-col cols="2">
        <v-btn color="blue" @click="mostrarFormulario = !mostrarFormulario">Novo produto</v-btn>
      </v-col>
      <v-col cols="1">
        <v-icon color="blue">mdi-filter</v-icon>
      </v-col>
    </v-row>

    
    <!-- Tabela de Caixas -->
    <v-data-table :headers="columnsTable" :items="produtos">
      <template v-slot:item="{ item }">
        <tr>
          <td class="text-center">#{{ item.id }}</td>
          <td class="text-center">{{ item.nome }}</td>
          <td class="text-center">{{ item.descricao }}</td>
          <td class="text-center">{{ item.preco }}</td>
          <td class="text-center">
            <!-- Botão Editar -->
            <v-btn class="mr-2" color="yellow" icon>
              <v-icon>mdi-pencil</v-icon>
            </v-btn>

            <!-- Botão Excluir -->
            <v-btn class="mr-2" color="red" icon @click="deletarProduto(item.id)">
        <v-icon>mdi-delete</v-icon>
      </v-btn>

      <v-col cols="12" v-if="mostrarFormulario">
      <v-form @submit.prevent="adicionarProduto">
        <!-- Campos do formulário -->
        <v-text-field v-model="novoProduto.nome" label="Nome"></v-text-field>
      <v-text-field v-model="novoProduto.descricao" label="Descrição"></v-text-field>
      <v-text-field v-model="novoProduto.preco" label="Preço" type="number"></v-text-field>
      <v-btn type="submit" color="blue">Adicionar Produto</v-btn>
      </v-form>
    </v-col>

 
          </td>
        </tr>
      </template>
    </v-data-table>
  </v-container>
</template>
