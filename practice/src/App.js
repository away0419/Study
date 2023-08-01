import * as React from "react";
import Box from "@mui/material/Box";
import { DataGrid } from "@mui/x-data-grid";
import "./App.css";

function App() {
  return (
    <Box sx={{ height: 400, width: "100%" }}>
      <DataGrid
        rows={rows}
        columns={columns}
        initialState={{
          pagination: {
            paginationModel: {
              pageSize: 5,
            },
          },
        }}
        pageSizeOptions={[5]}
        checkboxSelection
        disableRowSelectionOnClick
        experimentalFeatures={{ columnGrouping: true }}
        columnGroupingModel={columnGroupingModel}
      />
    </Box>
  );
}

const columns = [
  { field: "lrgCd", headerName: "코드", width: 90 },
  { field: "lrgNm", headerName: "명", width: 90 },
  { field: "mdlCd", headerName: "코드", width: 90 },
  { field: "mdlNm", headerName: "명", width: 90 },
  { field: "detail1", headerName: "상세내용1", width: 90 },
];

const rows = [
  {
    id: 1,
    lrgCd: "C001",
    lrgNm: "고객",
    mdlCd: "M001",
    mdlNm: "상담",
    detail1: "상세내용",
  },
];

const columnGroupingModel = [
  {
    groupId: "대분류",
    children: [{ field: "lrgCd" }, { field: "lrgNm" }],
  },
  {
    groupId: "중분류",
    children: [{ field: "mdlCd" }, { field: "mdlNm" }],
  },
  {
    field: "detail1",
    headerName: "상세내용1",
    width: 90,
  },
];

export default App;
