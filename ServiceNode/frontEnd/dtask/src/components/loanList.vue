<template>
	<div>
		<div>
			<label style="margin-left: 10px;">账户：</label>
			<el-input v-model="accountID" style="width: 300px;"></el-input>
			<label style="margin-left: 10px;">还款状态：</label>
			<el-select v-model="repaymentStatus" placeholder="请选择">
				<el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
				</el-option>
			</el-select>
			<el-button type="primary" @click="list_button()" icon="el-icon-search" style="background: #24375E;border: 0px ;margin-left: 10px;">搜索</el-button>
		</div>
		<el-table :data="loanList" style="width: 100%">
			<el-table-column prop="account" label="账号" width="200">
			</el-table-column>
			<el-table-column prop="amount" label="数额" width="150">
			</el-table-column>
			<el-table-column prop="loanDate" label="贷款时间" width="250">
			</el-table-column>
			<el-table-column label="查看" align="center" min-width="100">
				<template slot-scope="scope">
					<el-button type="text" @click="getDetail(scope.row)">查看详情</el-button>
				</template>
			</el-table-column>

		</el-table>
		<el-pagination @current-change="search_page" :current-page="currentLoanPage" layout=" prev, pager, next" :total="loanPage"
		 align='center'>
		</el-pagination>

		<el-dialog title="提示" :visible.sync="dialogVisible" width="30%">
			<label>账号：</label><span>{{account}}</span></br></br>
			<label>金额：</label><span>{{amount}}</span></br></br>
			<label>贷款时间：</label><span>{{loanDate}}</span></br></br>
			<label>应还款时间：</label><span>{{scheduledPayment}}</span></br></br>
			<label>保证类型：</label><span>{{guaranteeType}}</span></br></br>
			<label>保证文件：</label><span>{{guaranteeInfo}}</span></br></br>
			<label>用途：</label><span>{{usage}}</span>
			<span slot="footer" class="dialog-footer">
				<el-button type="primary" @click="dialogVisible = false">确定</el-button>
			</span>
		</el-dialog>

	</div>
</template>

<script>
	import axios from 'axios';
	export default {
		data() {
			return {
				id: null,
				loanList: [],
				accountID: '',
				loanPage: null,
				currentLoanPage: 1,
				dialogVisible: false,
				content: null,
				author: null,
				dialogVisible_edit: false,
				title_detail: null,
				options:[
					{
						label:'未还款',
						value:0
					},
					{
						label:'已还款',
						value:1
					},
				]
			}
		},
		methods: {
			getDefaultLoanList() {
				axios.get('/api/loanSearch/loanPage', {
						params: {
							accountID: '',
							repaymentStatus:-1
						},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						this.loanPage = res.data * 10;
					})
					.catch(err => {
						this.$alert('请求失败', '提示', {
							confirmButtonText: '确定',
						});
					});



				axios.get('/api/loanSearch/loan', {
						params: {
							accountID: '',
							repaymentStatus:-1,
							page: 1,
						},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						this.loanList = eval(res.data);
					})
					.catch(err => {
						this.$alert('请求失败', '提示', {
							confirmButtonText: '确定',
						});
					});
			},
			search_page(currentPage) {
				this.currentLoanPage = currentPage;
				axios.get('/api/loanSearch/loan', {
						params: {
							accountID: this.accountID,
							repaymentStatus:-1,
							page: this.currentLoanPage,
						},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						this.loanList = eval(res.data);
					})
					.catch(function(error) {
						this.$alert('请求失败', '提示', {
							confirmButtonText: '确定',
						});
					});
			},
			list_button() {
				axios.get('/api/loanSearch/loan', {
						params: {
							accountID: this.accountID,
							repaymentStatus:-1,
							page: 1,
						},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						this.loanList = eval(res.data);
						this.currentLoanPage = 1;
					})
					.catch(err => {
						this.$alert('请求失败', '提示', {
							confirmButtonText: '确定',
						});
					});

				axios.get('/api/loanSearch/loanPage', {
						params: {
							accountID: this.accountID,
							repaymentStatus:-1,
						},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						this.currentLoanPage = res.data * 10;
					})
					.catch(err => {
						this.$alert('请求失败', '提示', {
							confirmButtonText: '确定',
						});
					});
			},
			getDetail(row) {
				this.dialogVisible = true;
				this.account = row.account;
				this.amount = row.amount;
				this.loanDate = row.loanDate;
				this.rate = row.rate;
				this.scheduledPayment = row.scheduledPayment;
				this.guaranteeType = row.guaranteeType;
				this.guaranteeInfo = row.guaranteeInfo;
				this.usage = row.usage;
			},
			handleLoan(row, result) {
				var parmas = new URLSearchParams();
				parmas.append("id", row.id);
				parmas.append("result", result);
				axios.post('/api/loan/handleLoanApply', parmas, {
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						if (res.da == "HANDLE_SUCCESS") {
							this.$alert('处理完成', '提示', {
								confirmButtonText: '确定',
							});
						} else {
							this.$alert('处理失败', '提示', {
								confirmButtonText: '确定',
							});
						}

					}).catch(err => {
						this.$alert('请求失败', '提示', {
							confirmButtonText: '确定',
						});
					});
			}

		},
		beforeMount: function() {
			this.getDefaultLoanList();
		}
	}
</script>

<style>

</style>
