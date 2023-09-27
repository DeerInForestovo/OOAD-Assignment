<template>
  <div id="MainPage">

    <el-container>


      <el-header class="header">

        <el-row>
          <el-col :span="12" id="MainPageTitle">
            <h1>Manage System</h1>
          </el-col>

<!--reference: https://blog.csdn.net/wanghu20150107010129/article/details/131110061-->
          <el-col :span="12" id="ToggleDarkButton">
            <el-text> Switch Dark Mode </el-text>
            <el-button text class="switch" :class="isDark ? 'isDark-switch' : 'noDark-switch'" @click="toggleDark">
              <el-icon v-if="isDark"> <Sunny/> </el-icon>
              <el-icon v-else> <Moon/> </el-icon>
            </el-button>
            <br>
            <el-text> System Time: {{this.systemTime}} </el-text>
          </el-col>
        </el-row>


      </el-header>


      <el-main>
        <el-row>
          <el-col :span="20">

            <el-tabs :tab-position="'left'" id="TabRoot">
              <el-tab-pane label="Conference Room List">

                <h2 id="ConferenceRoomListTitle">Conference Room List</h2>

          <!--    Table    -->

                <el-table
                    id="ConferenceRoomTable"
                    :data="conferenceRooms"
                    style="width: 80%;;margin: 0 auto;"
                    :header-cell-style="{'text-align':'center'}"
                    :cell-style="{'text-align':'center'}"
                    max-height="440"
                >

                  <el-table-column prop="RoomName" label="Room Name" width="150"/>
                  <el-table-column prop="Department" label="Department" width="150"/>
                  <el-table-column prop="Type" label="Type" width="150"/>

                  <el-table-column label="Location" width="300">
                    <template #default="scope">
                      <el-text>{{ buildingOptions[scope.row.Building] }}</el-text>
                      <br>
                      <el-text>Room: {{ scope.row.Room }}</el-text>
                    </template>
                  </el-table-column>

                  <el-table-column label="Date" width="150">
                    <template #default="scope">
                      <el-text>{{ (new Date(scope.row.Date)).toLocaleDateString() }}</el-text>
                    </template>
                  </el-table-column>

                  <el-table-column label="Start Time" width="150">
                    <template #default="scope">
                      <el-text>{{ (new Date(scope.row.TimeRange[0])).toLocaleTimeString() }}</el-text>
                    </template>
                  </el-table-column>

                  <el-table-column prop="EndTime" label="End Time" width="150">
                    <template #default="scope">
                      <el-text>{{ (new Date(scope.row.TimeRange[1])).toLocaleTimeString() }}</el-text>
                    </template>
                  </el-table-column>

                  <el-table-column prop="MaxDuration" label="MaxDuration" width="150">
                    <template #default="scope">
                      <el-text>{{ scope.row.MaxDuration }} hour(s)</el-text>
                    </template>
                  </el-table-column>

                  <el-table-column label="Operation" width="150" fixed="right">
                    <template #default="scope">
                      <el-button type="primary" size="small" @click="editRoom(scope.$index)">Edit</el-button>
                      <br>
                      <el-popconfirm
                          title="Are you sure to delete this?"
                          @confirm="deleteRoom(scope.$index)">
                        <template #reference>
                          <el-button type="danger" size="small">Delete</el-button>
                        </template>
                      </el-popconfirm>
                    </template>
                  </el-table-column>

                </el-table>

          <!--    Add a New Conference Room Button    -->

                <div id="AddConferenceRoomButton">
                  <br>
                  <el-button type="primary" size="large" @click="createNewRoom">
                    Add a New Conference Room
                  </el-button>
                </div>

          <!--    Dialog    -->

                <el-dialog
                    id="ConferenceRoomDialog"
                    v-model="dialogVisible"
                    :title="dialogTitle"
                    width="75%"
                    :before-close="handleClose"
                >

                  <el-form
                      id="ConferenceRoomDialogForm"
                      ref="ConferenceRoomForm"
                      :model="ConferenceRoomForm"
                      :rules="rules"
                      label-width="auto"
                      label-position="right"
                  >

            <!--        Room and Type        -->

                    <el-form-item label="Room Name" prop="RoomName">
                      <el-input v-model="ConferenceRoomForm.RoomName" style="width: 40%"/>
                    </el-form-item>
                    <el-form-item label="Department" prop="Department">
                      <el-input v-model="ConferenceRoomForm.Department" style="width: 40%"/>
                    </el-form-item>
                    <el-form-item label="Type" prop="Type">
                      <el-radio-group v-model="ConferenceRoomForm.Type">
                        <el-radio label="Small" model-value="Small">Small</el-radio>
                        <el-radio label="Medium" model-value="Medium">Medium</el-radio>
                        <el-radio label="Big" model-value="Big">Big</el-radio>
                      </el-radio-group>
                    </el-form-item>

            <!--        Location        -->

                    <div style="display: flex">
                      <el-form-item label="Location" prop="Building" style="flex: 1">
                        <el-select
                            style="width: 100%"
                            label="Building"
                            v-model="ConferenceRoomForm.Building"
                            placeholder="* Select a building"
                        >
                          <el-option
                              v-for="(item, index) in buildingOptions"
                              :key="item"
                              :label="item"
                              :value="index"
                          />
                        </el-select>
                      </el-form-item>
                      <el-form-item prop="Room" style="flex: 1">
                        <el-input v-model="ConferenceRoomForm.Room" style="width: 20%"/>
                      </el-form-item>
                    </div>

            <!--        Date and Time Range        -->

                    <el-form-item label="Date" prop="Date">
                      <el-date-picker
                          v-model="ConferenceRoomForm.Date"
                          type="date"
                          :disabled-date="disabledDate"
                      />
                    </el-form-item>
                    <el-form-item label="Time Range" prop="TimeRange" style="width: 50%">
                      <el-time-picker
                          is-range
                          range-separator="To"
                          start-placeholder="Start time"
                          end-placeholder="End time"
                          v-model="ConferenceRoomForm.TimeRange"
                      />
                    </el-form-item>
                    <el-form-item label="Max Duration" prop="MaxDuration">
                      <el-input v-model="ConferenceRoomForm.MaxDuration" style="width: 10%"/>
                      <el-text>hours</el-text>
                    </el-form-item>

                  </el-form>

                  <template #footer>
                    <span class="dialog-footer">
                      <el-button type="danger" @click="handleCancel">Cancel</el-button>
                      <el-button type="primary" @click="dialogConfirm">Confirm</el-button>
                    </span>
                  </template>
                </el-dialog>

              </el-tab-pane>
              <el-tab-pane label="More...">Nothing yet</el-tab-pane>
            </el-tabs>

          </el-col>

          <el-col :span="4" class="Portrait">
            <!--Arona-->
            <!--https://sm.ms/image/IwYom18CQDkXZVG-->
            <!--<img src="../assets/Arona.jpg" v-if="isDark"/>-->
            <el-image :fit="'contain'" :src="'https://s2.loli.net/2023/09/27/IwYom18CQDkXZVG.jpg'" v-if="isDark"></el-image>
            <!--Prona-->
            <!--<img src="../assets/Prona.jpg" v-else/>-->
            <!--https://sm.ms/image/uaFBRgt64kWf9I8-->
            <el-image :fit="'contain'" :src="'https://s2.loli.net/2023/09/27/uaFBRgt64kWf9I8.jpg'" v-else></el-image>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import {Moon, Sunny} from "@element-plus/icons-vue";
const isDark = ref(true)

const toggleDark = () => {
  // reference: https://blog.csdn.net/wanghu20150107010129/article/details/131110061
  isDark.value = !isDark.value
  const html = document.querySelector('html')
  if (html) {
    if (isDark.value) {
      html.classList.remove("dark");
      html.classList.add("light");
    } else {
      html.classList.remove("light");
      html.classList.add("dark");
    }
  }
}
</script>


<script>
import {ElMessageBox, ElNotification} from "element-plus";

export default {
  name: 'ConferenceRoomTable',
  data() {
    const roomNameValidator = (rule, value, callback) => {
      const re = /^[A-Za-z]+[0-9]+$/;
      if(!value) {
        return callback(new Error('Please input room name.'));
      }
      if(!re.test(value)) {
        return callback(new Error('Invalid room name.'));
      }
      callback();
    };

    const departmentNameValidator = (rule, value, callback) => {
      const re = /^[A-Z][A-Za-z\s]*$/;
      if(!value) {
        return callback(new Error('Please input department name.'));
      }
      if(!re.test(value)) {
        return callback(new Error('Invalid department name.'));
      }
      callback();
    };

    const dateValidator = (rule, value, callback) => {
      let nowDate = new Date();
      if(nowDate.getDate() >= new Date(value)) {
        return callback(new Error('The date selected should be after the current date.'));
      }
      callback();
    };

    const roomValidator = (rule, value, callback) => {
      const re = /^[1-9][0-9]{0,3}[ABC]?$/;
      if(!value) {
        return callback(new Error('Please input room name.'));
      }
      if(!re.test(value)) {
        return callback(new Error('Invalid room name.'));
      }
      callback();
    };

    const maxDurationValidator = (rule, value, callback) => {
      if(!value) {
        return callback(new Error('Please input a positive number.'));
      }
      let num = Number(value, 10);
      if(isNaN(num)) {
        return callback(new Error('Please input a positive number.'));
      }
      if(num <= 0) {
        return callback(new Error('Please input a positive number.'));
      }
      callback();
    }

    return {
      conferenceRooms: [
        {
          RoomName: "Room1",
          Department: "Electrical",
          Type: "Small",
          Building: 0,
          Room: "426A",
          Date: (new Date()).setFullYear(2023,11,30),
          TimeRange: [
            (new Date()).setHours(8, 0, 0),
            (new Date()).setHours(10, 0, 0),
          ],
          MaxDuration: 2,
        },
        {
          RoomName: "Room2",
          Department: "Computer Science",
          Type: "Big",
          Building: 1,
          Room: "434A",
          Date: (new Date()).setFullYear(2023,8,30),
          TimeRange: [
            (new Date()).setHours(10, 0, 0),
            (new Date()).setHours(17, 0, 0),
          ],
          MaxDuration: 4,
        },
        {
          RoomName: "Room3",
          Department: "Physics",
          Type: "Medium",
          Building: 2,
          Room: "101",
          Date: (new Date()).setFullYear(2023,9,15),
          TimeRange: [
            (new Date()).setHours(14, 0, 0),
            (new Date()).setHours(19, 0, 0),
          ],
          MaxDuration: 1,
        },
      ],
      ConferenceRoomForm: {},
      rules: {
        RoomName:[
          {validator: roomNameValidator, trigger: 'blur'},
          {required: true},
        ],
        Type: [
          {required: true, message: "Please choose the type of the room.", trigger: true}
        ],
        Department: [
          {validator: departmentNameValidator, trigger: 'blur'},
          {required: true},
        ],
        Date: [
          {validator: dateValidator, trigger: 'blur'},
          {required: true},
        ],
        Building: [
          {required: true, message: "Please choose a building.", trigger: true}
        ],
        Room: [
          {validator: roomValidator, trigger: 'blur'},
          {required: true},
        ],
        TimeRange: [
          {required: true, message: "Please choose the time range."},
        ],
        MaxDuration: [
          {validator: maxDurationValidator, trigger: 'blur'},
          {required: true},
        ],
      },
      buildingOptions: [
        'Teaching Building No.1 Lecture Hall',
        'Research Building Lecture Hall',
        'Library Conference Hall',
        'South Building',
      ],
      dialogVisible: false,
      editingIndex: -1,
      dialogTitle: '',
      systemTime: new Date().toLocaleTimeString('en-US'),
    }
  },

  methods: {
    deleteRoom(index) {
      this.conferenceRooms.splice(index, 1);
      ElNotification({
        title: 'Success',
        message: 'Deleted.',
        type: 'success',
        duration: 1500,
      })
    },
    editRoom(index) {
      this.editingIndex = index
      this.ConferenceRoomForm = JSON.parse(JSON.stringify(this.conferenceRooms[index]))
      console.log(this.ConferenceRoomForm)
      this.dialogTitle = 'Edit Room Information'
      this.dialogVisible = true
      console.log(this.ConferenceRoomForm)
    },
    createNewRoom() {
      this.editingIndex = -1
      this.ConferenceRoomForm = {
        RoomName: "",
        Department: "",
        Type: "",
        Building: "",
        Room: "",
        Date: new Date().setDate(new Date().getDate() + 1),
        TimeRange: [new Date().setSeconds(0), new Date().setSeconds(0)],
        MaxDuration: "",
      }
      this.dialogTitle = 'Add a New Room'
      this.dialogVisible = true
      console.log(this.ConferenceRoomForm)
    },
    dialogConfirm() {
      this.$refs['ConferenceRoomForm'].validate((valid) => {
        if(valid) {
          console.log(this.ConferenceRoomForm)
          this.dialogVisible = false
          this.ConferenceRoomForm['MaxDuration'] = Number(this.ConferenceRoomForm['MaxDuration'])
          console.log(this.ConferenceRoomForm)
          if(this.editingIndex === -1)
            this.conferenceRooms.push(this.ConferenceRoomForm)
          else
            this.conferenceRooms[this.editingIndex] = this.ConferenceRoomForm
          ElNotification({
            title: 'Success',
            message: 'Your modification has been saved.',
            type: 'success',
            duration: 1500,
          })
        }
      })
    },
    handleClose(done) {
      ElMessageBox.confirm('Your changes will not be saved.', 'Are you sure to leave?')
          .then(() => {
            ElNotification({
              title: 'Info',
              message: 'Canceled.',
              type: 'info',
              duration: 1500,
            })
            done()
          })
          .catch(() => {
          })
    },
    handleCancel() {
      ElMessageBox.confirm('Your changes will not be saved.', 'Are you sure to leave?')
          .then(() => {
            ElNotification({
              title: 'Info',
              message: 'Canceled.',
              type: 'info',
              duration: 1500,
            })
            this.dialogVisible = false
          })
          .catch(() => {
          })
    },
    disabledDate(time) {
      return time.getTime() <= Date.now()
    },
    refreshSystemTime(){
      this.systemTime = new Date().toLocaleTimeString('en-US')
    }
  },

  mounted() {
    setInterval(this.refreshSystemTime,1000)
  }
}
</script>


<style scoped>

.header {
  font-family: Cambria, serif;
}

#ConferenceRoomListTitle {
  text-align: center;
}

#AddConferenceRoomButton {
  text-align: center;
}

#ToggleDarkButton {
  text-align: right;
}

.Portrait {
  width: 300px;
  height: 500px;
}

.Portrait img {
  width: 100%;
  height: 100%;

}

</style>