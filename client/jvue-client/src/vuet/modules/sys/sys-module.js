import ApiUtils from '@/api/'

export default {
  modules: {
    list: {
      data () {
        return {
          searchForm: {
            page: 1,
            pageSize: 10,
            pageCount: 0,
            totalCount: 0
          },
          loading: false,
          list: []
        }
      },
      async fetch () {
        this.loading = true
        const param = {
          page: this.searchForm.page,
          pageSize: this.searchForm.pageSize
        }

        const response = await ApiUtils.get('/api/module', param)
        this.loading = false
        let {status, data = {}} = response
        if (status === 200 && data.error === null) {
          this.searchForm.page = data.pageNum
          this.searchForm.pageSize = data.pageSize
          this.searchForm.pageCount = data.pages
          this.searchForm.totalCount = data.total
          this.list = data.data || []
        }
      },
      async remove (id) {
        const response = await ApiUtils.delete(`/api/module/${id}`)
        let {error, message, data} = response.data
        if (error === null) {
          return data
        } else {
          Promise.reject(message)
        }
      }
    },
    add: {
      data () {
        return {
          form: {}
        }
      },
      async fetch () {
        // 初始化信息
      },
      async save () {
        let param = this.form
        const response = await ApiUtils.post('/api/module', param)
        let {error, message, data} = response.data
        if (error === null) {
          return data
        } else {
          Promise.reject(message)
        }
      }
    },
    detail: {
      data () {
        return {
          detail: {}
        }
      },
      async fetch () {
        let id = this.app.$route.params['id']
        const response = await ApiUtils.get(`/api/module/${id}`)
        let {error, message, data} = response.data
        if (error === null) {
          this.detail = data
          return data
        } else {
          Promise.reject(message)
        }
      }
    },
    edit: {
      data () {
        return {
          form: {}
        }
      },
      async fetch () {
        let id = this.app.$route.params['id']
        const response = await ApiUtils.get(`/api/module/${id}`)
        let {error, message, data} = response.data
        if (error === null) {
          this.form = data
          return data
        } else {
          Promise.reject(message)
        }
      },
      async save () {
        let id = this.app.$route.params['id']
        let param = this.form
        const response = await ApiUtils.put(`/api/module/${id}`, param)
        let {error, message, data} = response.data
        if (error === null) {
          return data
        } else {
          Promise.reject(message)
        }
      }
    },
    names: {
      data () {
        return {
          list: []
        }
      },
      async fetch () {
        const response = await ApiUtils.get('/api/module/ext/names')
        let {status, data = {}} = response
        if (status === 200 && data.error === null) {
          this.list = data.data || []
        }
      }
    }
  }
}
