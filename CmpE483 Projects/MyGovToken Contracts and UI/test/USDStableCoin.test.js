const { expect } = require("chai");
const { ethers } = require("hardhat");

// Test suite for USDStableCoin contract
describe("USDStableCoin", function () {
  // Test case: Deploy USDStableCoin and perform tests
  it("Should deploy USDStableCoin and perform tests", async function () {
    // Get the contract factory for USDStableCoin
    const USDStableCoin = await ethers.getContractFactory("USDStableCoin");

    // Deploy the USDStableCoin contract
    const usdStableCoin = await USDStableCoin.deploy();

    // Retrieve the total supply of USDStableCoin as a string
    const totalSupply = (await usdStableCoin.totalSupply()).toString();

    // Perform the assertion: Check if the total supply is equal to the expected value "10000"
    expect(totalSupply).to.equal("10000");
  });
});
