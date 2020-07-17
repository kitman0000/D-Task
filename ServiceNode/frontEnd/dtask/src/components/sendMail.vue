<template>
	<div>
		<el-form label-width="80px">
			<el-form-item label="部门">
				<el-select v-model="value6" placeholder="请选择" @change="getUser()">
					<el-option v-for="item in options3" :key="item.value" :label="item.label" :value="item.value">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="收件人">
				<el-select v-model="value5" multiple placeholder="请选择">
					<el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="标题">
				<el-input v-model="title"></el-input>
			</el-form-item>
			<el-form-item label="内容">
				<el-input v-model="content" type="textarea"></el-input>
			</el-form-item>
			<el-form-item label="是否紧急">
				<el-select filterable placeholder="请选择状态" ref="isImportantSelector" v-model="IsImportantValue" style="width: 150px;">
					<el-option v-for="item in options2" :key="item.value" :label="item.label" :value="item.value">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="文件上传">
				<el-upload class="upload-demo" action="https://jsonplaceholder.typicode.com/posts/" :on-change="handleChange"
				 :file-list="fileList3">
					<el-button type="primary" icon="el-icon-plus" style="background: #24375E;border: 0px ;">添加文件</el-button>	
				</el-upload>
				<el-button type="primary" @click="sendFile()" icon="el-icon-check" style="background: #24375E;border: 0px ;">确认上传的文件</el-button>
			</el-form-item>
		</el-form>
		<el-button type="primary" @click="sendMail()" icon="el-icon-check" style="background: #24375E;border: 0px ;">确认上传</el-button>
	</div>
</template>

<script>
	import axios from 'axios';
	import qs from 'qs';
	export default {
		data() {
			return {
				options: [],
				options2: [{
					value: true,
					label: '紧急信息'
				}, {
					value: false,
					label: '普通信息'
				}],
				options3:[],
				value5: [],
				title: '',
				content: '',
				IsImportantValue: true,
				fileList3: [],
				value6:[],
				fileData:[],
			}
		},
		methods: {
			handleChange(file, fileList) {
				this.fileList3 = fileList.slice(-3);
			},
			department(){
				axios.get('/api/department/department', {
						params: {},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						var response = res.data.data;
						var a = eval(response);
						a.forEach((res)=>{
							this.options3.push({
								value: res.id,
								label:res.departmentName
								});
						})
					})
					.catch(function(error) {
						this.$alert('请求部门失败', '提示', {
						         confirmButtonText: '确定',
						       });
					});
			},
			getUser(){
				console.log(this.value6);
				axios.get('/api/user/userList', {
						params: {
							username:"",
							nickname:"",
							phone:"",
							email:"",
							onboardDateStart:"",
							onboardDateEnd:"",
							roleID:-1,
							departmentID:this.value6,
							birthdayStart:"",
							birthdayEnd:"",
							page:1,
						},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						this.options = [];
						var response = res.data.data;
						var a = eval(response);
						a.forEach((res)=>{
							this.options.push({
								value: res.id,
								label:res.username
								});
						})

						console.log(this.options);
					})
					.catch(function(error) {
						this.$alert('请求用户失败', '提示', {
						         confirmButtonText: '确定',
						       });
					});
			},
			sendMail(){
				var parmas = new FormData();
				parmas.append("receiverID",this.value5);
				parmas.append("title",this.title);
				parmas.append("content",this.content);
				parmas.append("isImportant",this.IsImportantValue);
				parmas.append("filename",this.fileData);
				axios.post('/api/mail/mail', parmas, {
					paramsSerializer: parmas => {
					      return qs.stringify(parmas, { indices: false })
					    },
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						var response = res.data;
						if (response.ret == 1) {
							this.$alert('上传成功', '提示', {
							         confirmButtonText: '确定',
							       });
								   this.$router.push({path:'/GetSendedMail'});
						} else {
							this.$alert('上传失败', '提示', {
							         confirmButtonText: '确定',
							       });
						}
					});
				
				
			},
			sendFile(){
				console.log(this.fileList3);
				for(var i = 0;i<this.fileList3.length;i++){
					var fileParmas = new FormData();
					fileParmas.append('file',this.fileList3[i].raw);
					axios.post('/api/mail/attachment', fileParmas, {
							headers: {
								"token": localStorage.getItem("token"),
							}
						})
						.then(res=> {
							var response = res.data.data;
							this.fileData.push(response);
							this.$alert('文件上传成功', '提示', {
							         confirmButtonText: '确定',
							       });
						});
						
				}
			}
		},
		beforeMount: function() {
				this.department();
		}
	}
</script>

<style>
</style>
