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
			<el-button type="primary" @click="searchMail_searchButton()" icon="el-icon-search" style="background: #24375E;border: 0px ;margin-left: 10px;">搜索</el-button>
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
			<el-pagination
			  @current-change="searchMail"
			  :current-page="currentPage"
			  :page-size="10"
			  layout="prev, pager, next, jumper"
			  :total="pageNumber">
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
					value: 1,
					label: '紧急信息'
				}, {
					value: 0,
					label: '普通信息'
				},{
					value: -1,
					label: '全部'
				},],
			
				options2: [{
					value: 1,
					label: '已读'
				}, {
					value: 0,
					label: '未读'
				},{
					value: -1,
					label: '全部'
				}],
				tableData2:[],
				IsImportantValue: -1,
				hasReadValue: -1,
				dateValue:'',
				pageNumber:1,
				currentpage:1,
				keyword:'',
			}
		},
		methods:{
			getDefaultMailPage(){
				axios.get('/api/mail/mailPage', {
						params: {
							keyword:'',
							hasRead:'',
							isImportant:'',
							sendTimeStart:'',
							sendTimeEnd:'',
						},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						var response = res.data;
						this.pageNumber = response.data * 10;
					})
					.catch(function(error) {
						this.$alert('请求失败', '提示', {
						         confirmButtonText: '确定',
						       });
					});
			},
			getDefaultMailList(){
				axios.get('/api/mail/mailList', {
						params: {
							keyword:'',
							hasRead:'',
							isImportant:'',
							sendTimeStart:'',
							sendTimeEnd:'',
							page:1,
						},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						var response = res.data.data;
						this.tableData2=[];
						var a = eval(response);
						for(var i=0;i<a.length;i++){
							a[i].sendTime = new Date(a[i].sendTime).toLocaleDateString().replace(/\//g, '-');
						}
						this.tableData2 =a ;
					})
					.catch(function(error) {
						this.$alert('请求失败', '提示', {
						         confirmButtonText: '确定',
						       });
					});
			},
			searchMail(currentpage){
				var hasRead,isImportant;
				var sendTimeEnd,sendTimeStart;
				if(this.IsImportantValue == -1){
					isImportant = '';
				}
				else if(this.IsImportantValue == 0){
					isImportant = false;
				}
				else{
					isImportant = true;
				}
				if(this.hasReadValue == -1){
					hasRead = '';
				}
				else if(this.IsImportantValue == 0){
					hasRead = false;
				}
				else{
					hasRead = true;
				}
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
							hasRead:hasRead,
							isImportant:isImportant,
							sendTimeStart:sendTimeStart,
							sendTimeEnd:sendTimeEnd,
						},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						var response = res.data;
						this.pageNumber = response.data * 10;
					})
					.catch(function(error) {
						this.$alert('请求失败', '提示', {
						         confirmButtonText: '确定',
						       });
					});
				
				axios.get('/api/mail/mailList', {
				    params: {
				      keyword:this.keyword,
				      hasRead:hasRead,
				      isImportant:isImportant,
				      sendTimeStart:sendTimeStart,
				      sendTimeEnd:sendTimeEnd,
					  page:currentpage,
				    },headers:{
					"token":localStorage.getItem("token"),
				}
				  })
				  .then(res=>{
						var response=res.data.data;
						this.tableData2=[];
						var a = eval(response);
						for(var i=0;i<a.length;i++){
							a[i].sendTime = new Date(a[i].sendTime).toLocaleDateString().replace(/\//g, '-');
						}
						this.tableData2 =a ;
					})
				  .catch(err=> {
					  this.$alert('请求失败', '提示', {
					           confirmButtonText: '确定',
					         });
				  });
			},
			searchMail_searchButton(){
				var hasRead,isImportant;
				var sendTimeEnd,sendTimeStart;
				if(this.IsImportantValue == -1){
					isImportant = '';
				}
				else if(this.IsImportantValue == 0){
					isImportant = false;
				}
				else{
					isImportant = true;
				}
				if(this.hasReadValue == -1){
					hasRead = '';
				}
				else if(this.IsImportantValue == 0){
					hasRead = false;
				}
				else{
					hasRead = true;
				}
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
							hasRead:hasRead,
							isImportant:isImportant,
							sendTimeStart:sendTimeStart,
							sendTimeEnd:sendTimeEnd,
						},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						var response = res.data;
						this.pageNumber = response.data *10;
					})
					.catch(function(error) {
						this.$alert('请求失败', '提示', {
						         confirmButtonText: '确定',
						       });
					});
				
				
				axios.get('/api/mail/mailList', {
				    params: {
				      keyword:this.keyword,
				      hasRead:hasRead,
				      isImportant:isImportant,
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
						var a = eval(response);
						for(var i=0;i<a.length;i++){
							a[i].sendTime = new Date(a[i].sendTime).toLocaleDateString().replace(/\//g, '-');
						}
						this.tableData2 =a ;
					})
				  .catch(err=> {
					  this.$alert('请求失败', '提示', {
					           confirmButtonText: '确定',
					         });
				  });
			},
			MailDetail(row,event){
				localStorage.setItem("chosenMail",row.id);
				localStorage.setItem("sender",row.sender);
				this.$router.push({path:"/MailDetail"});
			}
		},
		beforeMount:function() {
			this.getDefaultMailList();
			this.getDefaultMailPage();
		}
		}
</script>

<style>
	
</style>
