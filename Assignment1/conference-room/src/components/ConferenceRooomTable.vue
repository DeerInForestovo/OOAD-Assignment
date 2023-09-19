<template>
  <div>
    <h2 id="a">Conference Room List</h2>

<!--    Back to top    -->

    <el-backtop :right="100" :bottom="100" />

<!--    Table    -->

    <el-table
        :data="conferenceRooms"
        style="width: 80%;;margin: 0 auto;"
        :header-cell-style="{'text-align':'center'}"
        :cell-style="{'text-align':'center'}"
    >

      <el-table-column prop="RoomName" label="Room Name" width="150"/>
      <el-table-column prop="Department" label="Department" width="150"/>
      <el-table-column prop="Type" label="Type" width="150"/>

      <el-table-column label="Location" width="300">
        <template #default="scope">
          <el-text>{{ scope.row.Building }}</el-text>
          <br>
          <el-text>Room: {{ scope.row.Room }}</el-text>
        </template>
      </el-table-column>

      <el-table-column prop="Date" label="Date" width="150"/>

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
          <el-text>{{ scope.row.MaxDuration }} hours</el-text>
        </template>
      </el-table-column>

      <el-table-column label="Operation" width="150" fixed="right">
        <template #default="scope">
          <el-button type="primary" size="small" @click="editRoom(scope.$index)">Edit</el-button>
          <br>
          <el-button type="danger" size="small" @click="deleteRoom(scope.$index)">Delete</el-button>
        </template>
      </el-table-column>

    </el-table>

<!--    Add a New Conference Room Button    -->

    <br>
    <br>
    <br>

    <el-button type="primary" size="large" @click="createNewRoom">
      Add a New Conference Room
    </el-button>

<!--    Dialog    -->

    <el-dialog
        v-model="dialogVisible"
        title="Add a New Conference Room"
        width="75%"
        :before-close="handleClose"
    >

      <el-form
          ref="ConferenceRoomForm"
          :model="ConferenceRoomForm"
          :rules="rules"
          label-width="auto"
          label-position="right"
      >
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

        <div style="display: flex">
          <el-form-item label="Location" prop="Building" style="flex: 1">
            <el-select
                style="width: 100%"
                label="Building"
                v-model="ConferenceRoomForm.Building"
                placeholder="* Select a building"
            >
              <el-option
                  v-for="item in BuildingOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item prop="Room" style="flex: 1">
            <el-input v-model="ConferenceRoomForm.Room" style="width: 20%"/>
          </el-form-item>
        </div>
        <el-form-item label="Time Range" prop="TimeRange" style=" width: 50%">
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
          <el-button type="primary" @click="addRoom">Confirm</el-button>
        </span>
      </template>
    </el-dialog>

  </div>
</template>


<script>
import {ElMessageBox, ElNotification} from "element-plus";


export default {
  name: 'ConferenceRoomTable',
  data() {
    const roomNameValidator = (rule, value, callback) => {
      const re = /^[A-Za-z0-9]*$/;
      if(!value) {
        return callback(new Error('Please input room name.'));
      }
      if(!re.test(value)) {
        return callback(new Error('Invalid room name.'));
      }
      callback();
    };

    const departmentNameValidator = (rule, value, callback) => {
      const re = /^[A-Za-z]*$/;
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

    const maxDurationValidator = (rule, value, callback) => {
      const re = /^[0-9]*$/;
      if(!value) {
        return callback(new Error('Please input max duration.'));
      }
      if(!re.test(value)) {
        return callback(new Error('Max duration should be a number.'));
      }
      callback();
    };

    const roomValidator = (rule, value, callback) => {
      const re = /^[A-Za-z0-9]*$/;
      if(!value) {
        return callback(new Error('Please input room name.'));
      }
      if(!re.test(value)) {
        return callback(new Error('Invalid room name.'));
      }
      callback();
    };

    return {
      conferenceRooms: [
        {
          "RoomName": "Room1",
          "Department": "Electrical",
          "Type": "Small",
          "Building": "South Building",
          "Room": "426A",
          "Date": "2023/12/10",
          "TimeRange": [
              (new Date()).setHours(8, 0, 0),
              (new Date()).setHours(10, 0, 0),
          ],
          "MaxDuration": "2",
        },
        // {
        //   "RoomName": "Room2",
        //   "Department": "Computer Science",
        //   "Type": "Big",
        //   "Building": "Teaching Building No.1 Lecture Hall",
        //   "Room": "434A",
        //   "Date": "2023/12/14",
        //   "StartTime": "00:00",
        //   "EndTime": "24:00",
        //   "MaxDuration": "4",
        // },
        // {
        //   "RoomName": "Room3",
        //   "Department": "Physics",
        //   "Type": "Medium",
        //   "Building": "Library Conference Hall",
        //   "Room": "101",
        //   "Date": "2023/12/15",
        //   "StartTime": "09:00",
        //   "EndTime": "17:00",
        //   "MaxDuration": "1",
        // },
      ],
      ConferenceRoomForm: {
        RoomName: "",
        Department: "",
        Type: "",
        Building: "",
        Room: "",
        Date: "",
        TimeRange: [],
        MaxDuration: "",
      },
      rules: {
        RoomName:[
          {validator: roomNameValidator, trigger: 'blur'},
          {required: true, trigger: true},
        ],
        Type: [
          {required: true, message: "Please choose the type of the room.", trigger: true}
        ],
        Department: [
          {validator: departmentNameValidator, trigger: 'blur'},
          {required: true, trigger: true},
        ],
        Date: [
          {validator: dateValidator, trigger: 'blur'},
          {required: true, trigger: true},
        ],
        Building: [
          {required: true, message: "Please choose a building.", trigger: true}
        ],
        Room: [
          {validator: roomValidator, trigger: 'blur'},
          {required: true, trigger: true},
        ],
        // TimeRange: [
        //   {required: true, message: "Please choose the time range.", trigger: true},
        // ],
        MaxDuration: [
          {validator: maxDurationValidator, trigger: 'blur'},
          {required: true, trigger: true},
        ],
      },
      BuildingOptions: [
        {
          value: 'Teaching Building No.1 Lecture Hall',
          label: 'Teaching Building No.1 Lecture Hall',
        },
        {
          value: 'Research Building Lecture Hall',
          label: 'Research Building Lecture Hall',
        },
        {
          value: 'Library Conference Hall',
          label: 'Library Conference Hall',
        },
        {
          value: 'South Building',
          label: 'South Building',
        }
      ],
      dialogVisible: false
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
      this.ConferenceRoomForm = this.conferenceRooms[index]
      this.dialogVisible = true
      this.conferenceRooms[index] = this.ConferenceRoomForm
    },
    createNewRoom() {
      this.ConferenceRoomForm = {}
      this.dialogVisible = true
      this.conferenceRooms.push(this.ConferenceRoomForm)
    },
    addRoom() {
      this.$refs['ConferenceRoomForm'].validate((valid) => {
        if(valid) {
          console.log(this.ConferenceRoomForm)
          this.dialogVisible = false
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
    }
  }
}
</script>
<style scoped>

</style>