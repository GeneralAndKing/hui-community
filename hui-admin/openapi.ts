import fs from "node:fs";
import openapiTS, { astToString } from "openapi-typescript";
import ts from "typescript";

const DATE = ts.factory.createTypeReferenceNode(ts.factory.createIdentifier("Date")); // `Date`
const NULL = ts.factory.createLiteralTypeNode(ts.factory.createNull()); // `null`
const BLOB = ts.factory.createTypeReferenceNode(ts.factory.createIdentifier("Blob")); // `Blob`

openapiTS("http://139.155.2.12:8080/v3/api-docs", {
  transform(schemaObject, metadata) {
    if (schemaObject.format === "date-time") {
      return schemaObject.nullable
        ? ts.factory.createUnionTypeNode([DATE, NULL])
        : DATE;
    }
    if (schemaObject.format === "binary") {
      return schemaObject.nullable
        ? ts.factory.createUnionTypeNode([BLOB, NULL])
        : BLOB;
    }
  },
  exportType: true,
  enum: true
})
  .then(res => {
    const contents = astToString(res);
    fs.writeFileSync("./src/types/client.d.ts", contents);
  })
