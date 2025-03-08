import { v4 as uuidv4 } from 'uuid';

const generateUUIDArray = (count = 100) => {
  return Array.from({ length: count }, () => uuidv4());
};

const uuidArray = generateUUIDArray();
console.log(uuidArray);