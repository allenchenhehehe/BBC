// Todo 輸入框、刪除、流水號(crypto.randomUUID();)
// ref()、reactive()
/**
 * npm init vue@latest
 * 
 * npm install bootstrap  
 * import "bootstrap/dist/css/bootstrap.min.css"; // 樣式框架安裝
 * 
 * npm install --save-dev json-server@latest
 * npm run mock-api //本程式執行此行即可
 * "mock-api": "json-server --watch db.json --port 3001" //npx 本地虛擬json資料檔(這行是把DB檔案放在外部用的)
 * 
 * npm i bootstrap-icons
 * import 'bootstrap-icons/font/bootstrap-icons.css'
 */
<script setup>
  import { onMounted, ref, reactive } from "vue";
  import axios from "axios";

  const tab = ref("firstPage");

  const toDoList = ref([]);       // 存資料
  const newToDo = reactive({
    title: '',
    content: ''
  })        // 新增任務輸入框
  const apiUrl = 'http://localhost:3001/toDoList'; // JSON Server 位置
  
// 讀取資料（GET）
  async function loadData() {
    try {
      const res = await axios.get(apiUrl)
      toDoList.value = res.data
    } catch (err) {
      console.error('讀取失敗:', err)
    }
  }

// 新增任務（POST）
  async function addTask() {
    if (!newToDo.title.trim()) alert("標題不得為空"); return
    try {
      const res = await axios.post(apiUrl, {
        id: crypto.randomUUID(), //自動取流水號
        name: newToDo.title,
        content: newToDo.content,
        isChecked: false
      })
      toDoList.value.unshift(res.data)
      newToDo.value = ''
    } catch (err) {
      console.error('新增失敗:', err)
    }
  }

//更新資料
    async function updateTask(id, name, content) {
    try {
      updateToDo = toDoList.value.find(i => i.id == id)
      updateToDo.name = name
      updateToDo.content = content
      toDoList.value = toDoList.value.map(i => i.id === id ? updateToDo : i)
      const response = await axios.put(`${apiUrl}/${id}`, updateToDo)

    } catch (err) {
      console.error('修改失敗:', err)
    }
  }

// 切換勾選狀態（PATCH）
  async function toggleCheck(item) {
    try {
      item.isChecked = !item.isChecked
      await axios.patch(`${apiUrl}/${item.id}`, { isChecked: item.isChecked })
    } catch (err) {
      console.error('更新失敗:', err)
    }
  }

// 刪除項目（DELETE）
  async function deleteTask(id) {
  if (confirm("確定要刪除此筆資料？")) {
    try {
      await axios.delete(`${apiUrl}/${id}`) // 這行是對DB操作
      toDoList.value = toDoList.value.filter(t => t.id !== id) // 這行是對前端的陣列操作
    } catch (err) {
      console.error('刪除失敗:', err)
    }
  }
  }
  
  onMounted(() => {
    loadData();
  })
  
</script>

<template>
  <div class="container mt-4">
    <h1 class="text-center mb-4">資料總覽</h1>

    <!-- 頁籤切換 -->
    <ul class="nav nav-tabs mb-3 justify-content-center">
      <li class="nav-item">
        <button class="nav-link" :class="{ active: tab === 'firstPage' }" @click="tab = 'firstPage'">
          直式表格
        </button>
      </li>
      <li class="nav-item">
        <button class="nav-link" :class="{ active: tab === 'secondPage' }" @click="tab = 'secondPage'">
          橫式表格
        </button>
      </li>
    </ul>

    <div class="mb-3 d-flex gap-2">
        <input class="form-control" style="max-height: 40px;" v-model="newToDo.title" placeholder="輸入標題" />
        <input class="form-control" style="max-height: 40px;" v-model="newToDo.content" placeholder="輸入內容" />
        <button class="btn btn-primary" style="max-height: 40px;" @click="addTask">
          <i class="bi bi-plus-circle"></i>
        </button>
    </div>

    <!-- 第一頁：直式表格 -->
    <div v-show="tab === 'firstPage'" class="card p-3 shadow-sm">
      <table class="table table-bordered text-center align-middle">
        <thead class="table-primary">
          <tr>
            <!-- <th>項目ID</th> -->
            <th>項目名稱5</th>
            <th>項目內容</th>
            <th>完成與否</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in toDoList" :key="item.id" :class="item.isChecked ? 'table-success' : 'table-danger'">
            <!-- <td>{{ item.id }}</td> -->
            <td>{{ item.name }}</td>
            <td>{{ item.content }}</td>
            <td>
              <input type="checkbox" @click="toggleCheck(item)" v-model="item.isChecked"></input>
            </td>
            <td>
              <button @click="deleteTask(item.id)" type="button" class="btn btn-danger">
                 <i class="bi bi-trash3"></i>
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- 第二頁：橫式表格 -->
    <div v-show="tab === 'secondPage'" class="card p-3 shadow-sm">
      <table class="table table-bordered">
        <thead class="table-primary">
          <tr>
            <th class=" text-center">標題</th>
            <th class=" text-center">內容</th>
          </tr>
        </thead>
        <tbody>
          <tr><th class="table-light w-25 text-center">姓名</th><td>王小明</td></tr>
          <tr><th class="table-light text-center">年齡</th><td>28</td></tr>
          <tr><th class="table-light text-center">職業</th><td>工程師</td></tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<style scoped></style>
