<template>
	<div>
		<div>
			<label style="margin-left: 10px;">关键词：</label>
			<el-input placeholder="关键词" v-model="keyword" style="width: 150px;"></el-input>
			<label style="margin-left: 15px;">是否紧急：</label>
			<el-select filterable placeholder="请选择状态" ref="isImportantSelector" v-model="IsImportantValue" style="width: 100px;">
				<el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
				</el-option>
			</el-select>
			<label style="margin-left: 15px;">是否已读：</label>
			<el-select filterable placeholder="请选择状态" ref="hasReadSelector" v-model="hasReadValue" style="width: 100px;">
				<el-option v-for="item in options2" :key="item.value" :label="item.label" :value="item.value">
				</el-option>
			</el-select>
		
			<label style="margin-left: 15px;">日期选择：</label>
			<el-date-picker v-model="dateValue" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
			</el-date-picker>
		
			<el-button type="primary" icon="el-icon-search" @click="searchMail_searchButton()" style="margin-left: 10px;">搜索</el-button>
		</div>
		<el-table :data="tableData2" style="width: 100%"  @row-click="MailDetail">
			<el-table-column prop="id" label="邮件id" width="200">
			</el-table-column>
			<el-table-column prop="sender" label="发件人" width="200">
			</el-table-column>
			<el-table-column prop="title" label="标题" width="220">
			</el-table-column>
			<el-table-column prop="sendTime" label="发送时间" width="280">
			</el-table-column>
		</el-table>
		<div style="text-align: center;">
			<el-pagination background layout="prev, pager, next" :total="pageNumber" :current-page.sync="currentpage" ref="currentPage" @current-change="searchMail" >
			</el-pagination>
		</div>
	</div>
</template>

<script>
	import axios from 'axios';
	export default {
		data() {
			return {
				options: [{
					value: true,
					label: '紧急信息'
				}, {
					value: false,
					label: '普通信息'
				}],
			
				options2: [{
					value: true,
					label: '已读'
				}, {
					value: false,
					label: '未读'
				}],
				tableData2:[],
				IsImportantValue: true,
				hasReadValue: false,
				dateValue:'',
				pageNumber:1,
				currentpage:0,
				keyword:'',
			}
		},
		methods:{
			getDefaultMailPage(){
				var date = new Date();
				var nowMonth = date.getMonth() + 1;
				var strDate = date.getDate()+1;
				var seperator = "-";
				var nowDate = date.getFullYear() + seperator + nowMonth + seperator + strDate;
				axios.get('/api/mail/mailPage', {
						params: {
							keyword:'',
							hasRead:false,
							isImportant:true,
							sendTimeStart:'2020-1-1',
							sendTimeEnd:nowDate,
						},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						var response = res.data.data;
						this.tableData2 = eval(response);
						this.pageNumber = response.data;
					})
					.catch(function(error) {
						console.log(error);
					});
			},
			getDefaultMailList(){
				var date = new Date();
				var nowMonth = date.getMonth() + 1;
				var strDate = date.getDate();
				var seperator = "-";
				var nowDate = date.getFullYear() + seperator + nowMonth + seperator + strDate;
				axios.get('/api/mail/mailList', {
						params: {
							keyword:' ',
							hasRead:false,
							isImportant:true,
							sendTimeStart:'2020-1-1',
							sendTimeEnd:nowDate,
							page:1,
						},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						var response = res.data;
						this.pageNumber = response.data;
					})
					.catch(function(error) {
						console.log(error);
					});
			},
			searchMail(currentpage){
				var sendTimeEnd,sendTimeStart;
				console.log(this.dateValue);
				if(this.dateValue == ''){
					sendTimeStart = '';
					sendTimeEnd = '';
				}
				else
				{
					sendTimeStart = this.dateValue[0];
					sendTimeEnd = this.dateValue[1];
				}
				axios.get('/api/mail/mailPage', {
						params: {
							keyword:this.keyword,
							hasRead:this.$refs.hasReadSelector.value,
							isImportant:this.$refs.isImportantSelector.value,
							sendTimeStart:sendTimeStart,
							sendTimeEnd:sendTimeEnd,
						},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						var response = res.data;
						this.pageNumber = response.data;
					})
					.catch(function(error) {
						console.log(error);
					});
				
				axios.get('/api/mail/mailList', {
				    params: {
				      keyword:this.keyword,
				      hasRead:this.$refs.hasReadSelector.value,
				      isImportant:this.$refs.isImportantSelector.value,
				      sendTimeStart:sendTimeStart,
				      sendTimeEnd:sendTimeEnd,
					  page:currentpage,
				    },headers:{
					"token":localStorage.getItem("token"),
				}
				  })
				  .then(res=>{
						var response=res.data;
						this.tableData2=[];
						this.tableData2=eval(response);
						this.currentpage= currentpage;
					})
				  .catch(err=> {
					  
				  });
			},
			searchMail_searchButton(){
				var sendTimeEnd,sendTimeStart;
				console.log(this.dateValue);
				if(this.dateValue == ''){
					sendTimeStart = '';
					sendTimeEnd = '';
				}
				else
				{
					sendTimeStart = this.dateValue[0];
					sendTimeEnd = this.dateValue[1];
				}
				axios.get('/api/mail/mailPage', {
						params: {
							keyword:this.keyword,
							hasRead:this.$refs.hasReadSelector.value,
							isImportant:this.$refs.isImportantSelector.value,
							sendTimeStart:sendTimeStart,
							sendTimeEnd:sendTimeEnd,
						},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						var response = res.data;
						this.pageNumber = response.data;
					})
					.catch(function(error) {
						console.log(error);
					});
				
				
				axios.get('/api/mail/mailList', {
				    params: {
				      keyword:this.keyword,
				      hasRead:this.$refs.hasReadSelector.value,
				      isImportant:this.$refs.isImportantSelector.value,
				      sendTimeStart:sendTimeStart,
				      sendTimeEnd:sendTimeEnd,
					  page:this.currentpage,
				    },headers:{
					"token":localStorage.getItem("token"),
				}
				  })
				  .then(res=>{
						var response=res.data.data;
						this.tableData2=[];
						this.tableData2=eval(response);
						this.currentpage= currentpage;
					})
				  .catch(err=> {
					  
				  });
			},
			MailDetail(row,event){
				localStorage.setItem("chosenMail",row.id);
				localStorage.setItem("sender",row.sender);
				this.$router.push({path:"/MailDetail"});
			}
		},
		beforeMount:function() {
			this.getDefaultMailPage();
			this.getDefaultMailList();
		}
		}
</script>

<style>
	
</style>
