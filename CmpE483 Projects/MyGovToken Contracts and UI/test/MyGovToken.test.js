// const { expect } = require("chai");

const chai = require("chai");
const chaiAsPromised = require("chai-as-promised");

chai.use(chaiAsPromised);

const { expect } = chai;

// Test suite for MyGovToken contract
describe("MyGovToken", function () {
  let MyGovToken;
  let myGovTokenInstance;
  let account;

  // Before each test case, deploy the MyGovToken contract
  beforeEach(async () => {
    [deployer, account] = await ethers.getSigners();

    MyGovToken = await ethers.getContractFactory("MyGovToken");

    // Deploy the contract with initial supply and a specified USD Stable Coin address
    myGovTokenInstance = await MyGovToken.connect(deployer).deploy(
      20000000,
      "0xd9145CCE52D386f254917e481eB44e9943F39138"
    );
    await myGovTokenInstance.deployed();
  });

  // Test case: Verify that the contract is deployed successfully
  it("should deploy MyGovToken contract", async () => {
    expect(await myGovTokenInstance.name()).to.equal("MyGov Token");
  });

  // Test case: Transfer tokens to NumOfAddresses addresses
  const NumOfAddresses = 300;
  it("should transfer tokens to 300 addresses", async () => {
    // Function to generate Ethereum addresses
    function generateAddresses(numAddresses) {
      const addresses = [];

      for (let i = 0; i < numAddresses; i++) {
        const wallet = ethers.Wallet.createRandom();
        addresses.push(wallet.address);
      }

      return addresses;
    }

    // Generate addresses and transfer tokens
    const addresses = generateAddresses(NumOfAddresses);
    let totalGasSpent = ethers.BigNumber.from(0);

    for (let i = 0; i < NumOfAddresses; i++) {
      const address = addresses[i];

      const tx = await myGovTokenInstance
        .connect(deployer)
        .transfer(address, ethers.utils.parseEther("0"));

      // Retrieve balance, gas spent, and log results
      const balance = await myGovTokenInstance.balanceOf(address);

      // Wait for the transaction to be mined and get the receipt
      const receipt = await tx.wait();

      // Log or document the results
      const gasSpent = receipt.gasUsed.mul(tx.gasPrice);
      totalGasSpent = totalGasSpent.add(gasSpent);

      console.log(
        `Address: ${address}, Balance: ${balance.toString()}, Gas Spent: ${ethers.utils.formatUnits(
          gasSpent / tx.gasPrice,
          0
        )}`
      );

      // Use assertions to check the results
      expect(balance.toString()).to.equal(
        ethers.utils.parseEther("0").toString(),
        `Incorrect balance for address ${i}`
      );
    }
  });

  // Test case: Donate MyGovToken
  it("should donate MyGovToken", async () => {
    // Initial balances
    const initialSenderBalance = await myGovTokenInstance.balanceOf(
      account.address
    );

    console.log(account);
    const initialContractBalance = await myGovTokenInstance.balanceOf(
      myGovTokenInstance.address
    );

    // Donate MyGovToken
    const donationAmount = 0;
    await myGovTokenInstance.connect(account).donateMyGovToken(donationAmount);

    // Updated balances after donation
    const finalSenderBalance = await myGovTokenInstance.balanceOf(
      account.address
    );
    const finalContractBalance = await myGovTokenInstance.balanceOf(
      myGovTokenInstance.address
    );

    // Log balances
    console.log("Initial Sender Balance:", initialSenderBalance.toString());
    console.log("Donation Amount:", donationAmount.toString());
    console.log("Final Sender Balance:", finalSenderBalance.toString());

    // Assertions
    expect(finalSenderBalance.toString()).to.equal(
      initialSenderBalance.sub(donationAmount).toString()
    );
    expect(finalContractBalance.toString()).to.equal(
      initialContractBalance.add(donationAmount).toString()
    );
  });

  // Test case: Revert if sender has insufficient balance
  it("should revert if sender has insufficient balance", async () => {
    // Attempt to donate more than the sender's balance
    const donationAmount = 10;
    await expect(
      myGovTokenInstance.connect(account).donateMyGovToken(donationAmount)
    ).to.be.rejectedWith("Insufficient amount of MyGovToken");
  });
});
